package com.districtnet.service;

import com.districtnet.dto.node.NodeCreateDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaSender {

    @Autowired
    private KafkaTemplate<String, NodeCreateDto> kafkaTemplate;

    public void send(NodeCreateDto nodeCreateDto) {
        kafkaTemplate.send("node", nodeCreateDto);
    }
}
