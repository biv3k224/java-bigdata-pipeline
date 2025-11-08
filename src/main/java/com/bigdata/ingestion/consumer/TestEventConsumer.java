package com.bigdata.ingestion.consumer;

import com.bigdata.ingestion.model.EventData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class TestEventConsumer {

    private static final Logger log = LoggerFactory.getLogger(TestEventConsumer.class);

    @KafkaListener(topics = "raw-events", groupId = "ingestion-group")
    public void consumeRawEvents(EventData eventData) {
        log.info("📥 RECEIVED EVENT - ID: {}, Type: {}, Source: {}", 
                eventData.getEventId(), 
                eventData.getEventType(), 
                eventData.getSource());
        
        log.info("Event Details: {}", eventData);
        
        // Simple processing for now
        processEvent(eventData);
    }

    private void processEvent(EventData eventData) {
        log.info("🔄 Processing event: {}", eventData.getEventId());
        
        // For now, just log the processing
        // In next steps, we'll add:
        // - Data validation
        // - Data enrichment  
        // - Storage to MongoDB
        // - Forward to processed-events topic
    }
}