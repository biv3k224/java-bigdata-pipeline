package com.bigdata.ingestion.spark.model;


import java.io.Serializable;

public class SparkEvent {

	private String eventId;
	private String source;
	private String eventType;
	private String timestamp;
	private String payload;
	
	public SparkEvent() {
		
	}
	
	public String getEventId() {
		return eventId;
	}
	
	public void setEventId(String eventId) {
		this.eventId = eventId;
	}
	
	public String getSource() {
		return source;
	}
	
	public void setSource(String source) {
		this.source = source;
	}
	public String getEventType() {
		return eventType;
	}
	
	public void setEventType(String eventType) {
		this.eventType = eventType;
	}
	public String getTimestamp() {
		return timestamp;
	}
	
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	public String getPayload() {
		return payload;
	}
	
	public void setPayload(String payload) {
		this.payload = payload;
	}
	
	@Override
	public String toString() {
		return "SparkEvent{" +
                "eventId='" + eventId + '\'' +
                ", source='" + source + '\'' +
                ", eventType='" + eventType + '\'' +
                ", timestamp='" + timestamp + '\'' +
                '}';	}
}
