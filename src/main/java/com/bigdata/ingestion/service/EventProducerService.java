package com.bigdata.ingestion.service;

import com.bigdata.ingestion.model.EventData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
public class EventProducerService {

    private static final String RAW_EVENTS_TOPIC = "raw-events";

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    public String sendEvent(EventData eventData) {
        String messageKey = UUID.randomUUID().toString();

        try {
            CompletableFuture<SendResult<String, Object>> future = 
                kafkaTemplate.send(RAW_EVENTS_TOPIC, messageKey, eventData);

            future.whenComplete((result, ex) -> {
                if (ex == null) {
                    log.info("Event sent successfully: key={}, offset={}", 
                             messageKey, result.getRecordMetadata().offset());
                } else {
                    log.error("Failed to send event: key={}, error={}", 
                              messageKey, ex.getMessage());
                }
            });

            return messageKey;
            
        } catch (Exception e) {
            log.error("Error sending event to Kafka: {}", e.getMessage());
            throw new RuntimeException("Failed to send event to Kafka", e);
        }
    }

    public void sendToTopic(String topic, String key, Object message) {
        kafkaTemplate.send(topic, key, message);
    }
}