package com.districtnet.service;

import com.districtnet.model.NodeCreateDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jcraft.jsch.*;


@Service
public class SshConnect {

    private final SshThreadMenager sshThreadMenager;

    @Autowired
    public SshConnect(SshThreadMenager sshThreadMenager) {
        this.sshThreadMenager = sshThreadMenager;
    }

    public void connect(NodeCreateDto dto) {
        sshThreadMenager.getExecutorFor(dto.getUserName());
        sshThreadMenager.submit(dto.getUserName(), () -> {
            Session session = null;
            try {
                JSch jsch = new JSch();
                session = jsch.getSession(dto.getUserName(), dto.getIpAddress(), 22);
                session.setPassword(dto.getAuthKey());
                session.setConfig("StrictHostKeyChecking", "no");
                session.connect();

                // Пример работы с каналом для выполнения команды
                // ChannelExec channel = (ChannelExec) session.openChannel("exec");
                // channel.setErrStream(System.err);
                // channel.setInputStream(null);
                // channel.setOutputStream(null);
                // InputStream in = channel.getInputStream();
                // OutputStream out = channel.getOutputStream();
                // channel.connect();

                // Если нужно, можно обработать вывод канала, например:
                // byte[] buffer = new byte[1024];
                // while (in.available() > 0) {
                //     int i = in.read(buffer, 0, 1024);
                //     if (i < 0) break;
                //     System.out.print(new String(buffer, 0, i));
                // }

            } catch (Exception e) {
                System.err.println("Failed to connect to SSH: " + e.getMessage());
            } finally {
                if (session != null && session.isConnected()) {
                    try {
                        session.disconnect();
                        System.out.println("SSH session closed successfully.");
                    } catch (Exception e) {
                        System.err.println("Failed to disconnect SSH session: " + e.getMessage());
                    }
                }
            }
        });
    }
}
