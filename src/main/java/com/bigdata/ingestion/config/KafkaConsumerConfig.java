package com.bigdata.ingestion.config;

import com.bigdata.ingestion.model.EventData;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConsumerConfig {

    @Bean
    public ConsumerFactory<String, EventData> consumerFactory() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(
            ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, 
            "localhost:9092");
        configProps.put(
            ConsumerConfig.GROUP_ID_CONFIG, 
            "ingestion-group");
        configProps.put(
            ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, 
            org.apache.kafka.common.serialization.StringDeserializer.class);
        configProps.put(
            ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, 
            JsonDeserializer.class);
        configProps.put(
            ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, 
            "earliest");
        configProps.put(
            JsonDeserializer.TRUSTED_PACKAGES, 
            "com.bigdata.ingestion.model");
        
        return new DefaultKafkaConsumerFactory<>(configProps);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, EventData> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, EventData> factory =
            new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }
}