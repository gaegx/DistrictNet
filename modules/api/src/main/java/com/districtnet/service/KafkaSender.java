package com.districtnet.service;

import com.districtnet.dto.node.NodeDisplayDto;
import com.districtnet.dto.task.TaskCreateDto;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaSender {

    private final KafkaTemplate<String, NodeDisplayDto> nodeKafkaTemplate;
    private final KafkaTemplate<String, TaskCreateDto> taskKafkaTemplate;

    public KafkaSender(KafkaTemplate<String, NodeDisplayDto> nodeKafkaTemplate,
                       KafkaTemplate<String, TaskCreateDto> taskKafkaTemplate) {
        this.nodeKafkaTemplate = nodeKafkaTemplate;
        this.taskKafkaTemplate = taskKafkaTemplate;
    }

    public void sendNode(NodeDisplayDto nodeDisplayDto) {
        nodeKafkaTemplate.send("node", nodeDisplayDto);
    }

    public void sendTask(TaskCreateDto taskCreateDto) {
        for (int partition = 0; partition < 2; partition++) {
            taskKafkaTemplate.send("task", partition, null, taskCreateDto);
        }
    }

}
