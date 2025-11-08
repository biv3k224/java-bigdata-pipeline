package com.bigdata.ingestion.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class KafkaMessage {

	private String key;
	private EventData eventData;
	private String topic;
	private Long timestamp;
}
