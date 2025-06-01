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
    public ResponseEntity<String> upload(@RequestParam("file") MultipartFile file,
                                         @RequestParam("remotePath") String remotePath) {
        try {
            File tempFile = File.createTempFile("upload_", "_" + file.getOriginalFilename());
            file.transferTo(tempFile);

            boolean success = ftpService.uploadFile(tempFile.getAbsolutePath(), remotePath);
            tempFile.delete();

            if (success) {
                return ResponseEntity.ok("Файл загружен на FTP.");
            } else {
                return ResponseEntity.status(500).body("Ошибка загрузки файла.");
            }
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Ошибка: " + e.getMessage());
        }
    }

    @GetMapping("/download")
    public ResponseEntity<String> download(@RequestParam("remotePath") String remotePath,
                                           @RequestParam("localPath") String localPath) {
        boolean success = ftpService.downloadFile(remotePath, localPath);
        if (success) {
            return ResponseEntity.ok("Файл загружен с FTP в: " + localPath);
        } else {
            return ResponseEntity.status(500).body("Ошибка скачивания файла.");
        }
    }

    @PostMapping("/mkdir")
    public ResponseEntity<String> createDir(@RequestParam("remoteDir") String remoteDir) {
        boolean success = ftpService.makeDirectory(remoteDir);
        if (success) {
            return ResponseEntity.ok("Директория создана.");
        } else {
            return ResponseEntity.status(500).body("Ошибка создания директории.");
        }
    }

    @DeleteMapping("/file")
    public ResponseEntity<String> deleteFile(@RequestParam("remoteFile") String remoteFile) {
        boolean success = ftpService.deleteFile(remoteFile);
        if (success) {
            return ResponseEntity.ok("Файл удалён.");
        } else {
            return ResponseEntity.status(500).body("Ошибка удаления файла.");
        }
    }

    @DeleteMapping("/dir")
    public ResponseEntity<String> deleteDir(@RequestParam("remoteDir") String remoteDir) {
        boolean success = ftpService.removeDirectory(remoteDir);
        if (success) {
            return ResponseEntity.ok("Директория удалена.");
        } else {
            return ResponseEntity.status(500).body("Ошибка удаления директории.");
        }
    }

    @GetMapping("/list")
    public ResponseEntity<List<String>> listFiles(@RequestParam("remoteDir") String remoteDir) {
        List<String> files = ftpService.listFiles(remoteDir);
        return ResponseEntity.ok(files);
    }
}
