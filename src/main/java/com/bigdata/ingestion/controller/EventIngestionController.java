package com.bigdata.ingestion.controller;

import com.bigdata.ingestion.model.EventData;
import com.bigdata.ingestion.model.EventDocument;
import com.bigdata.ingestion.service.EventProducerService;
import com.bigdata.ingestion.service.EventQueryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/v1/events")
public class EventIngestionController {

    @Autowired
    private EventProducerService eventProducerService;

    @Autowired
    private EventQueryService eventQueryService;  

    @PostMapping("/ingest")
    public ResponseEntity<Map<String, Object>> ingestEvent(@RequestBody EventData eventData) {
        try {
            // Set timestamp if not provided
            if (eventData.getTimestamp() == null) {
                eventData.setTimestamp(LocalDateTime.now());
            }
            
            // Generate event ID if not provided
            if (eventData.getEventId() == null) {
                eventData.setEventId(java.util.UUID.randomUUID().toString());
            }

            String messageKey = eventProducerService.sendEvent(eventData);
            
            Map<String, Object> response = new HashMap<>();
            response.put("status", "success");
            response.put("messageKey", messageKey);
            response.put("eventId", eventData.getEventId());
            response.put("timestamp", eventData.getTimestamp());
            
            log.info("Event ingested successfully: {}", eventData.getEventId());
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            log.error("Error ingesting event: {}", e.getMessage());
            
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("status", "error");
            errorResponse.put("message", "Failed to ingest event: " + e.getMessage());
            
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }

    @GetMapping("/health")
    public ResponseEntity<Map<String, String>> healthCheck() {
        Map<String, String> response = new HashMap<>();
        response.put("status", "UP");
        response.put("service", "data-ingestion-service");
        response.put("timestamp", LocalDateTime.now().toString());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/all")
    public ResponseEntity<List<EventDocument>> getAllEvents() {
        try {
            List<EventDocument> events = eventQueryService.getAllEvents();
            return ResponseEntity.ok(events);
        } catch (Exception e) {
            log.error("Error fetching events: {}", e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/count")
    public ResponseEntity<Map<String, Long>> getEventCount() {
        try {
            long count = eventQueryService.getTotalEventCount();
            Map<String, Long> response = new HashMap<>();
            response.put("count", count);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Error counting events: {}", e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/source/{source}")
    public ResponseEntity<List<EventDocument>> getEventsBySource(@PathVariable String source) {
        try {
            List<EventDocument> events = eventQueryService.getEventsBySource(source);
            return ResponseEntity.ok(events);
        } catch (Exception e) {
            log.error("Error fetching events by source: {}", e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/type/{eventType}")
    public ResponseEntity<List<EventDocument>> getEventsByType(@PathVariable String eventType) {
        try {
            List<EventDocument> events = eventQueryService.getEventsByType(eventType);
            return ResponseEntity.ok(events);
        } catch (Exception e) {
            log.error("Error fetching events by type: {}", e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }
}