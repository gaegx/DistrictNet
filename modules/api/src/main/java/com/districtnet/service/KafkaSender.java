package com.districtnet.service;

import com.districtnet.dto.node.NodeCreateDto;

import com.districtnet.dto.task.TaskCreateDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service

public class KafkaSender {

    private final KafkaTemplate<String, NodeCreateDto> kafkaTemplate;
    private final KafkaTemplate<String, TaskCreateDto> kafkaTemplate2;

    private KafkaSender(KafkaTemplate<String, NodeCreateDto> kafkaTemplate,KafkaTemplate<String, TaskCreateDto> kafkaTemplate2) {
        this.kafkaTemplate = kafkaTemplate;
        this.kafkaTemplate2 = kafkaTemplate2;
    }


    public void send(NodeCreateDto nodeCreateDto) {
        kafkaTemplate.send("node", nodeCreateDto);
    }

    public void send(TaskCreateDto taskCreateDto) {
        kafkaTemplate2.send("task", taskCreateDto);
    }
}
