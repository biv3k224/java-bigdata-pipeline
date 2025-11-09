package com.bigdata.ingestion.model;


import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Document(collection = "raw_events")
public class EventDocument {
	@Id
	private String id;
	private String eventId;
	private String source;
	private String eventType;
	private LocalDateTime timestamp;
	private Object payload;
	private EventData.Metadata metadata;
	private LocalDateTime storedAt;
	private String kafkaOffset;
	
	public EventDocument (EventData eventData) {
		this.eventId = eventData.getEventId();
		this.source = eventData.getSource();
		this.eventType = eventData.getEventType();
		this.timestamp = eventData.getTimestamp();
		this.payload = eventData.getPayload();
		this.metadata = eventData.getMetadata();
		this.storedAt = LocalDateTime.now();
	}

}
