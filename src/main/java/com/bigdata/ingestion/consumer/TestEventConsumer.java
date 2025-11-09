package com.bigdata.ingestion.consumer;

import com.bigdata.ingestion.model.EventData;
import com.bigdata.ingestion.model.EventDocument;
import com.bigdata.ingestion.repository.EventRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class TestEventConsumer {

    private static final Logger log = LoggerFactory.getLogger(TestEventConsumer.class);

    @Autowired
    private EventRepository eventRepository;

    @KafkaListener(topics = "raw-events", groupId = "ingestion-group")
    public void consumeRawEvents(EventData eventData) {
        log.info("📥 RECEIVED EVENT - ID: {}, Type: {}, Source: {}", 
                eventData.getEventId(), 
                eventData.getEventType(), 
                eventData.getSource());
        
        try {
            // Process and store the event
            processAndStoreEvent(eventData);
            log.info("✅ Event stored successfully: {}", eventData.getEventId());
            
        } catch (Exception e) {
            log.error("❌ Failed to process event: {}, Error: {}", 
                     eventData.getEventId(), e.getMessage());
        }
    }

    private void processAndStoreEvent(EventData eventData) {
        // Convert to document and save to MongoDB
        EventDocument document = new EventDocument(eventData);
        EventDocument savedDocument = eventRepository.save(document);
        
        log.info("💾 Stored in MongoDB with ID: {}", savedDocument.getId());
    }
}