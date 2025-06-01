package com.districtnet.config;


import lombok.extern.slf4j.Slf4j;
import org.apache.ftpserver.FtpServer;
import org.apache.ftpserver.FtpServerFactory;
import org.apache.ftpserver.ftplet.Authority;
import org.apache.ftpserver.listener.ListenerFactory;
import org.apache.ftpserver.usermanager.impl.BaseUser;
import org.apache.ftpserver.usermanager.impl.WritePermission;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class FtpServerStarter implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {
        FtpServerFactory serverFactory = new FtpServerFactory();
        String ftpPath = Paths.get("").toAbsolutePath() + "/ftp/data";
        Files.createDirectories(Paths.get(ftpPath));


        BaseUser user = new BaseUser();
        user.setName("user");
        user.setPassword("password");
        user.setHomeDirectory(ftpPath);


        List<Authority> authorities = new ArrayList<>();
        authorities.add(new WritePermission());
        user.setAuthorities(authorities);


        serverFactory.getUserManager().save(user);


        ListenerFactory factory = new ListenerFactory();
        factory.setPort(2221);
        serverFactory.addListener("default", factory.createListener());

        // Запуск сервера
        FtpServer server = serverFactory.createServer();
        server.start();

        log.info("✅ FTP-сервер запущен на порту 2221");
    }
}
