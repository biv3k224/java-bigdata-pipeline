package com.bigdata.ingestion.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EventData {
    private String eventId;
    private String source;
    private String eventType;
    private LocalDateTime timestamp;
    private Object payload;
    private Metadata metadata;
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Metadata {
        private String version = "1.0";
        private String environment;
        private String location;
        private String sourceIp;
    }
}