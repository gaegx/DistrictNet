package com.districnet.service.Impl;

import com.districnet.config.ActiveContex;
import com.districnet.dto.NodeDisplayDto;
import com.districnet.dto.TaskCreateDto;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;


@Service
public class KafkaListenerService {

    ActiveContex activeContex;

    public KafkaListenerService(ActiveContex activeContex) {
        this.activeContex = activeContex;

    }

    @KafkaListener(
            topics = "task",
            groupId = "ftp",
            containerFactory = "kafkaListenerTaskContainerFactory"
    )
    public void listenTask(TaskCreateDto dto) {
        System.out.println("Received TaskCreateDto from Kafka: " + dto.toString());
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

