package com.districtnet.service;

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

    private FTPClient connect() throws IOException {
        FTPClient ftpClient = new FTPClient();
        ftpClient.connect(HOST, PORT);
        ftpClient.login(USER, PASSWORD);
        ftpClient.enterLocalPassiveMode();
        ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
        return ftpClient;
    }

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

    private void disconnect(FTPClient ftpClient) {
        if (ftpClient.isConnected()) {
            try {
                ftpClient.logout();
                ftpClient.disconnect();
            } catch (IOException e) {
                log.warn("Ошибка при отключении от FTP: {}", e.getMessage(), e);
            }
        }
    }
}
