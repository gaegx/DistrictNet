package com.districnet.config;

import com.districnet.dto.NodeDisplayDto;
import com.districnet.dto.TaskCreateDto;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafka
public class KafkaConsumerConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Value("${spring.kafka.consumer.group-id1}")
    private String groupId1;

    @Value("${spring.kafka.consumer.group-id2}")
    private String groupId2;



    @Bean
    public ConsumerFactory<String, TaskCreateDto> consumerFactoryTask() {
        JsonDeserializer<TaskCreateDto> nodeCreateDtoJsonDeserializer = new JsonDeserializer<>(TaskCreateDto.class);
        nodeCreateDtoJsonDeserializer.setUseTypeHeaders(false);
        nodeCreateDtoJsonDeserializer.addTrustedPackages("*");

        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId1);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);

        return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(), nodeCreateDtoJsonDeserializer);


    }

    public ConsumerFactory<String, NodeDisplayDto> consumerFactoryNodeDisplay() {
        JsonDeserializer<NodeDisplayDto> nodeCreateDtoJsonDeserializer = new JsonDeserializer<>(NodeDisplayDto.class);
        nodeCreateDtoJsonDeserializer.setUseTypeHeaders(false);
        nodeCreateDtoJsonDeserializer.addTrustedPackages("*");

        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId2);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);

        return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(), nodeCreateDtoJsonDeserializer);


    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, TaskCreateDto> kafkaListenerTaskContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, TaskCreateDto> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactoryTask());
        return factory;
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, NodeDisplayDto> kafkaListenerNodeContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, NodeDisplayDto> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactoryNodeDisplay());
        return factory;
    }


}
