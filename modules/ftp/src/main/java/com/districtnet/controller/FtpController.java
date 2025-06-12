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
                                             @RequestParam("remotePath") String remotePath) throws IOException {
        File tempFile = File.createTempFile("upload", file.getOriginalFilename());
        file.transferTo(tempFile);

        boolean success = ftpService.uploadFile(tempFile.getAbsolutePath(), remotePath);
        tempFile.delete();

        return success ? ResponseEntity.ok("Файл загружен") : ResponseEntity.badRequest().body("Ошибка загрузки");
    }

    @PostMapping("/upload-dir")
    public ResponseEntity<String> uploadDirectory(@RequestParam String localDirPath,
                                                  @RequestParam String remoteDirPath) {
        boolean success = ftpService.uploadDirectory(localDirPath, remoteDirPath);
        return success ? ResponseEntity.ok("Каталог загружен") : ResponseEntity.badRequest().body("Ошибка загрузки каталога");
    }

    @GetMapping("/download")
    public ResponseEntity<String> downloadFile(@RequestParam String remoteFilePath,
                                               @RequestParam String localFilePath) {
        boolean success = ftpService.downloadFile(remoteFilePath, localFilePath);
        return success ? ResponseEntity.ok("Файл скачан") : ResponseEntity.badRequest().body("Ошибка загрузки");
    }

    @GetMapping("/download-dir")
    public ResponseEntity<String> downloadDirectory(@RequestParam String remoteDirPath,
                                                    @RequestParam String localDirPath) {
        boolean success = ftpService.downloadDirectory(remoteDirPath, localDirPath);
        return success ? ResponseEntity.ok("Каталог скачан") : ResponseEntity.badRequest().body("Ошибка загрузки каталога");
    }

    @GetMapping("/list")
    public ResponseEntity<List<String>> listFiles(@RequestParam String remoteDirPath) {
        return ResponseEntity.ok(ftpService.listFiles(remoteDirPath));
    }

    @PostMapping("/mkdir")
    public ResponseEntity<String> makeDir(@RequestParam String remoteDirPath) {
        return ftpService.makeDirectory(remoteDirPath)
                ? ResponseEntity.ok("Директория создана")
                : ResponseEntity.badRequest().body("Ошибка создания директории");
    }

    @DeleteMapping("/delete-file")
    public ResponseEntity<String> deleteFile(@RequestParam String remoteFilePath) {
        return ftpService.deleteFile(remoteFilePath)
                ? ResponseEntity.ok("Файл удален")
                : ResponseEntity.badRequest().body("Ошибка удаления файла");
    }

    @DeleteMapping("/delete-dir")
    public ResponseEntity<String> removeDir(@RequestParam String remoteDirPath) {
        return ftpService.removeDirectory(remoteDirPath)
                ? ResponseEntity.ok("Директория удалена")
                : ResponseEntity.badRequest().body("Ошибка удаления директории");
    }
}
