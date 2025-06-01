package com.districtnet.service;

import com.districtnet.entity.TaskCreateDto;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;


@Service
public class NodeKafkaListenerService {

    private FtpService ftpService;
    public NodeKafkaListenerService(FtpService ftpService) {
        this.ftpService = ftpService;
        ftpService.makeDirectory("docker");
        ftpService.makeDirectory("data");
    }
    @KafkaListener(
            topics = "task",
            groupId = "ftp",
            containerFactory = "kafkaListenerContainerFactory"
    )
    public void listen(TaskCreateDto dto) {
        System.out.println("Received DTO from Kafka: " + dto);
        ftpService.uploadFile(dto.getDockerPath(),"docker" );
        ftpService.u(dto.getDataPath(),"data" );



    }
}

