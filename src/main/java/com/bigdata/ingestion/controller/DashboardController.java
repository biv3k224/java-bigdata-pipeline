package com.bigdata.ingestion.controller;

import com.bigdata.ingestion.model.EventData;
import com.bigdata.ingestion.model.EventDocument;
import com.bigdata.ingestion.service.EventProducerService;
import com.bigdata.ingestion.service.EventQueryService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Controller
public class DashboardController {

    @Autowired
    private EventQueryService eventQueryService;

    @Autowired
    private EventProducerService eventProducerService;

    @GetMapping("/")
    public String showDashboard(Model model) {
        updateDashboardModel(model);
        return "dashboard";
    }

    @PostMapping("/send-event")
    public String sendEvent(@RequestParam String source,
                           @RequestParam String eventType,
                           @RequestParam String payload,
                           Model model) {
        
        try {
            // Parse JSON payload
            ObjectMapper mapper = new ObjectMapper();
            Object payloadObj = mapper.readValue(payload, Object.class);
            
            // Create event
            EventData eventData = new EventData();
            eventData.setSource(source);
            eventData.setEventType(eventType);
            eventData.setPayload(payloadObj);
            eventData.setTimestamp(LocalDateTime.now());
            eventData.setEventId(UUID.randomUUID().toString());
            
            // Send to Kafka
            String messageKey = eventProducerService.sendEvent(eventData);
            
            model.addAttribute("message", "✅ Event sent successfully! Key: " + messageKey);
            
        } catch (Exception e) {
            model.addAttribute("message", "❌ Error sending event: " + e.getMessage());
        }
        
        updateDashboardModel(model);
        return "dashboard";
    }

    // ADD THIS RESET ENDPOINT
 // In DashboardController.java - CHANGE FROM POST TO GET
    @GetMapping("/reset-events")  // Changed from @PostMapping
    public String resetAllEvents(Model model) {
        try {
            String result = eventQueryService.deleteAllEvents();
            model.addAttribute("message", "✅ " + result);
        } catch (Exception e) {
            model.addAttribute("message", "❌ Error resetting events: " + e.getMessage());
        }
        
        updateDashboardModel(model);
        return "dashboard";
    }

    private void updateDashboardModel(Model model) {
        // Get statistics
        long totalEvents = eventQueryService.getTotalEventCount();
        List<EventDocument> recentEvents = eventQueryService.getAllEvents();
        
        // Event counts by source
        Map<String, Long> eventsBySource = new HashMap<>();
        eventsBySource.put("website", (long) eventQueryService.getEventsBySource("website").size());
        eventsBySource.put("mobile-app", (long) eventQueryService.getEventsBySource("mobile-app").size());
        eventsBySource.put("sensor-1", (long) eventQueryService.getEventsBySource("sensor-1").size());
        eventsBySource.put("api-server", (long) eventQueryService.getEventsBySource("api-server").size());
        
        // Event counts by type
        Map<String, Long> eventsByType = new HashMap<>();
        eventsByType.put("PAGE_VIEW", (long) eventQueryService.getEventsByType("PAGE_VIEW").size());
        eventsByType.put("USER_LOGIN", (long) eventQueryService.getEventsByType("USER_LOGIN").size());
        eventsByType.put("PURCHASE", (long) eventQueryService.getEventsByType("PURCHASE").size());
        eventsByType.put("SEARCH", (long) eventQueryService.getEventsByType("SEARCH").size());
        eventsByType.put("ERROR", (long) eventQueryService.getEventsByType("ERROR").size());
        eventsByType.put("SENSOR_READING", (long) eventQueryService.getEventsByType("SENSOR_READING").size());

        model.addAttribute("totalEvents", totalEvents);
        model.addAttribute("recentEvents", recentEvents.size() > 5 ? recentEvents.subList(0, 5) : recentEvents);
        model.addAttribute("eventsBySource", eventsBySource);
        model.addAttribute("eventsByType", eventsByType);
        model.addAttribute("lastUpdated", LocalDateTime.now());
    }
}