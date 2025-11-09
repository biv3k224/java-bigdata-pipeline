package com.bigdata.ingestion.service;

import com.bigdata.ingestion.model.EventDocument;
import com.bigdata.ingestion.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class EventQueryService {

	@Autowired
	private EventRepository eventRepository;
	
	public List<EventDocument> getAllEvents(){
		return eventRepository.findAll();
	}
	
	public List<EventDocument> getEventsBySource(String source){
		return eventRepository.findBySource(source);
	}
	
	public List<EventDocument> getEventsByType(String eventType){
		return eventRepository.findByEventType(eventType);
	}
	
	public List<EventDocument> getEventsByTimeRang(LocalDateTime start, LocalDateTime end){
		return eventRepository.findByTimestampBetween(start, end);
	}
	
	public long getTotalEventCount() {
		return eventRepository.count();
	}
}
