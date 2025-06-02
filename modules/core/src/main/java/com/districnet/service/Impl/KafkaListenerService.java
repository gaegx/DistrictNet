package com.districnet.service.Impl;

import com.districnet.dto.TaskCreateDto;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;


@Service
public class KafkaListenerService {

    @KafkaListener(
            topics = "task",
            groupId = "ftp",
            containerFactory = "kafkaListenerContainerFactory"
    )
    public void listen(TaskCreateDto dto) {
        System.out.println("Received DTO from Kafka: " + dto);

    }
}

