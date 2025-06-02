package com.districtnet.service;

import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class FtpService {

    private static final String HOST = "localhost";
    private static final int PORT = 2221;
    private static final String USER = "user";
    private static final String PASSWORD = "password";

    public boolean uploadFile(String localFilePath, String remoteFilePath) {
        FTPClient ftpClient = new FTPClient();
        try (InputStream input = new FileInputStream(localFilePath)) {
            ftpClient.connect(HOST, PORT);
            ftpClient.login(USER, PASSWORD);
            ftpClient.enterLocalPassiveMode();
            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);

            return ftpClient.storeFile(remoteFilePath, input);
        } catch (IOException e) {
            log.error("Ошибка загрузки файла на FTP: {}", e.getMessage(), e);
            return false;
        } finally {
            disconnect(ftpClient);
        }
    }

    public boolean uploadDirectory(String localDirPath, String remoteDirPath) {
        FTPClient ftpClient = new FTPClient();
        try {
            ftpClient.connect(HOST, PORT);
            ftpClient.login(USER, PASSWORD);
            ftpClient.enterLocalPassiveMode();
            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);

            return uploadDirectoryRecursive(ftpClient, localDirPath, remoteDirPath);
        } catch (IOException e) {
            log.error("Ошибка загрузки каталога на FTP: {}", e.getMessage(), e);
            return false;
        } finally {
            disconnect(ftpClient);
        }
    }

    private boolean uploadDirectoryRecursive(FTPClient ftpClient, String localDirPath, String remoteDirPath) throws IOException {
        File localDir = new File(localDirPath);
        if (!localDir.exists() || !localDir.isDirectory()) {
            log.warn("Локальный путь не существует или не является директорией: {}", localDirPath);
            return false;
        }

        if (!ftpClient.makeDirectory(remoteDirPath)) {
            log.info("Каталог уже существует или не удалось создать: {}", remoteDirPath);
        }

        File[] files = localDir.listFiles();
        if (files == null) return true;

        for (File file : files) {
            String remoteFilePath = remoteDirPath + "/" + file.getName();
            String localFilePath = file.getAbsolutePath();

            if (file.isFile()) {
                try (InputStream input = new FileInputStream(file)) {
                    boolean success = ftpClient.storeFile(remoteFilePath, input);
                    if (!success) {
                        log.warn("Не удалось загрузить файл: {}", localFilePath);
                        return false;
                    }
                }
            } else if (file.isDirectory()) {
                boolean success = uploadDirectoryRecursive(ftpClient, localFilePath, remoteFilePath);
                if (!success) return false;
            }
        }
        return true;
    }

    public boolean downloadFile(String remoteFilePath, String localFilePath) {
        FTPClient ftpClient = new FTPClient();
        try (OutputStream output = new FileOutputStream(localFilePath)) {
            ftpClient.connect(HOST, PORT);
            ftpClient.login(USER, PASSWORD);
            ftpClient.enterLocalPassiveMode();
            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);

            return ftpClient.retrieveFile(remoteFilePath, output);
        } catch (IOException e) {
            log.error("Ошибка загрузки файла с FTP: {}", e.getMessage(), e);
            return false;
        } finally {
            disconnect(ftpClient);
        }
    }

    public boolean downloadDirectory(String remoteDirPath, String localDirPath) {
        FTPClient ftpClient = new FTPClient();
        try {
            ftpClient.connect(HOST, PORT);
            ftpClient.login(USER, PASSWORD);
            ftpClient.enterLocalPassiveMode();
            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);

            return downloadDirectoryRecursive(ftpClient, remoteDirPath, new File(localDirPath));
        } catch (IOException e) {
            log.error("Ошибка загрузки директории с FTP: {}", e.getMessage(), e);
            return false;
        } finally {
            disconnect(ftpClient);
        }
    }

    private boolean downloadDirectoryRecursive(FTPClient ftpClient, String remoteDirPath, File localDir) throws IOException {
        if (!localDir.exists()) {
            if (!localDir.mkdirs()) {
                log.warn("Не удалось создать локальную директорию: {}", localDir.getAbsolutePath());
                return false;
            }
        }

        FTPFile[] files = ftpClient.listFiles(remoteDirPath);
        for (FTPFile file : files) {
            String remoteFilePath = remoteDirPath + "/" + file.getName();
            File localFile = new File(localDir, file.getName());

            if (file.isFile()) {
                try (OutputStream output = new FileOutputStream(localFile)) {
                    boolean success = ftpClient.retrieveFile(remoteFilePath, output);
                    if (!success) {
                        log.warn("Не удалось скачать файл: {}", remoteFilePath);
                        return false;
                    }
                }
            } else if (file.isDirectory()) {
                boolean success = downloadDirectoryRecursive(ftpClient, remoteFilePath, new File(localDir, file.getName()));
                if (!success) return false;
            }
        }

        return true;
    }

    public boolean makeDirectory(String remoteDirPath) {
        FTPClient ftpClient = new FTPClient();
        try {
            ftpClient.connect(HOST, PORT);
            ftpClient.login(USER, PASSWORD);
            return ftpClient.makeDirectory(remoteDirPath);
        } catch (IOException e) {
            log.error("Ошибка создания директории: {}", e.getMessage(), e);
            return false;
        } finally {
            disconnect(ftpClient);
        }
    }

    public boolean deleteFile(String remoteFilePath) {
        FTPClient ftpClient = new FTPClient();
        try {
            ftpClient.connect(HOST, PORT);
            ftpClient.login(USER, PASSWORD);
            return ftpClient.deleteFile(remoteFilePath);
        } catch (IOException e) {
            log.error("Ошибка удаления файла: {}", e.getMessage(), e);
            return false;
        } finally {
            disconnect(ftpClient);
        }
    }

    public boolean removeDirectory(String remoteDirPath) {
        FTPClient ftpClient = new FTPClient();
        try {
            ftpClient.connect(HOST, PORT);
            ftpClient.login(USER, PASSWORD);
            return ftpClient.removeDirectory(remoteDirPath);
        } catch (IOException e) {
            log.error("Ошибка удаления директории: {}", e.getMessage(), e);
            return false;
        } finally {
            disconnect(ftpClient);
        }
    }

    public List<String> listFiles(String remoteDirPath) {
        FTPClient ftpClient = new FTPClient();
        List<String> fileNames = new ArrayList<>();
        try {
            ftpClient.connect(HOST, PORT);
            ftpClient.login(USER, PASSWORD);
            FTPFile[] files = ftpClient.listFiles(remoteDirPath);
            for (FTPFile file : files) {
                fileNames.add(file.getName());
            }
        } catch (IOException e) {
            log.error("Ошибка получения списка файлов: {}", e.getMessage(), e);
        } finally {
            disconnect(ftpClient);
        }
        return fileNames;
    }

    @PreDestroy
    private void disconnect(FTPClient ftpClient) {
        if (ftpClient != null && ftpClient.isConnected()) {
            try {
                ftpClient.logout();
                ftpClient.disconnect();
            } catch (IOException e) {
                log.warn("Ошибка при отключении от FTP: {}", e.getMessage(), e);
            }
        }
    }
}
