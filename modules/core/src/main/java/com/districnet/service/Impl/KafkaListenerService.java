package com.districnet.service.Impl;

import com.districnet.config.ActiveContex;
import com.districnet.dto.NodeDisplayDto;
import com.districnet.dto.TaskCreateDto;

import com.districnet.service.DataDistributorService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;


@Service
public class KafkaListenerService {

    private  final ActiveContex activeContex;
    private final DataDistributorService dataDistributorService;


    public KafkaListenerService(ActiveContex activeContex, DataDistributorService dataDistributorService) {
        this.activeContex = activeContex;
        this.dataDistributorService=dataDistributorService;

    }

    @KafkaListener(
            topics = "task",
            groupId = "ftp",
            containerFactory = "kafkaListenerTaskContainerFactory"
    )
    public void listenTask(TaskCreateDto dto) {
        System.out.println("Received TaskCreateDto from Kafka: " + dto.toString());
        dataDistributorService.distribute(dto);

    }

    @KafkaListener(
            topics = "node",
            groupId = "gaegxh",
            containerFactory = "kafkaListenerNodeContainerFactory"
    )
    public void listenNode(NodeDisplayDto dto) {
        System.out.println("Received NodeDisplayDto from Kafka: " + dto);
        activeContex.addNode(dto.getHostname(), dto);
    }

}

