package com.districtnet.service;

import com.districtnet.model.NodeCreateDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class NodeKafkaListenerService {


    private final SshConnect sshConnect;

    @Autowired
    public NodeKafkaListenerService(SshConnect sshConnect) {
        this.sshConnect = sshConnect;
    }

    @KafkaListener(topics = "node", groupId = "ssh", containerFactory = "kafkaListenerContainerFactory")
    public void listen(NodeCreateDto dto) {
        System.out.println("Received DTO from Kafka: " + dto);
        sshConnect.connect(dto);
    }
}
