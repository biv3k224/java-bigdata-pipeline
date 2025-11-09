package com.bigdata.ingestion.repository;

import com.bigdata.ingestion.model.EventDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface EventRepository extends MongoRepository<EventDocument, String> {
    
    // Find events by source
    List<EventDocument> findBySource(String source);
    
    // Find events by event type
    List<EventDocument> findByEventType(String eventType);
    
    // Find events within time range
    List<EventDocument> findByTimestampBetween(LocalDateTime start, LocalDateTime end);
    
    void deleteAll();
}