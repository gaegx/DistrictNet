package com.districtnet.controller;

import com.districtnet.service.FtpService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/ftp")
@RequiredArgsConstructor
public class FtpController {

    private final FtpService ftpService;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file,
                                             @RequestParam("remotePath") String remotePath) {
        try {
            File tempFile = File.createTempFile("upload_", "_" + file.getOriginalFilename());
            file.transferTo(tempFile);

            boolean success = ftpService.uploadFile(tempFile.getAbsolutePath(), remotePath);
            tempFile.delete();

            return success
                    ? ResponseEntity.ok("Файл загружен на FTP.")
                    : ResponseEntity.status(500).body("Ошибка загрузки файла.");
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Ошибка: " + e.getMessage());
        }
    }

    @PostMapping("/upload-dir")
    public ResponseEntity<String> uploadDirectory(@RequestParam("localDir") String localDir,
                                                  @RequestParam("remoteDir") String remoteDir) {
        boolean success = ftpService.uploadDirectory(localDir, remoteDir);
        return success
                ? ResponseEntity.ok("Директория загружена.")
                : ResponseEntity.status(500).body("Ошибка загрузки директории.");
    }

    @GetMapping("/download")
    public ResponseEntity<String> downloadFile(@RequestParam("remotePath") String remotePath,
                                               @RequestParam("localPath") String localPath) {
        boolean success = ftpService.downloadFile(remotePath, localPath);
        return success
                ? ResponseEntity.ok("Файл загружен с FTP.")
                : ResponseEntity.status(500).body("Ошибка скачивания файла.");
    }

    @GetMapping("/download-dir")
    public ResponseEntity<String> downloadDirectory(@RequestParam("remoteDir") String remoteDir,
                                                    @RequestParam("localDir") String localDir) {
        boolean success = ftpService.downloadDirectory(remoteDir, localDir);
        return success
                ? ResponseEntity.ok("Директория загружена.")
                : ResponseEntity.status(500).body("Ошибка скачивания директории.");
    }

    @PostMapping("/mkdir")
    public ResponseEntity<String> createDirectory(@RequestParam("remoteDir") String remoteDir) {
        boolean success = ftpService.makeDirectory(remoteDir);
        return success
                ? ResponseEntity.ok("Директория создана.")
                : ResponseEntity.status(500).body("Ошибка создания директории.");
    }

    @DeleteMapping("/file")
    public ResponseEntity<String> deleteFile(@RequestParam("remoteFile") String remoteFile) {
        boolean success = ftpService.deleteFile(remoteFile);
        return success
                ? ResponseEntity.ok("Файл удалён.")
                : ResponseEntity.status(500).body("Ошибка удаления файла.");
    }

    @DeleteMapping("/dir")
    public ResponseEntity<String> deleteDirectory(@RequestParam("remoteDir") String remoteDir) {
        boolean success = ftpService.removeDirectory(remoteDir);
        return success
                ? ResponseEntity.ok("Директория удалена.")
                : ResponseEntity.status(500).body("Ошибка удаления директории.");
    }

    @GetMapping("/list")
    public ResponseEntity<List<String>> listFiles(@RequestParam("remoteDir") String remoteDir) {
        List<String> files = ftpService.listFiles(remoteDir);
        return ResponseEntity.ok(files);
    }
}
