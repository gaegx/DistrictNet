package com.districtnet.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopic {

    @Bean
    public NewTopic topic1() {
        return TopicBuilder.name("node").build();
    }
    @Bean
    public NewTopic taskTopic() {
        return TopicBuilder.name("task")
                .partitions(2)
                .replicas(1)
                .build();
    }



}
