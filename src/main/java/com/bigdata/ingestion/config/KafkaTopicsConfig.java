package com.bigdata.ingestion.config;


import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaAdmin;


import java.util.*;

@Configuration
public class KafkaTopicsConfig {

	
	@Bean
	public NewTopic rawEventsTopic() {
		return new NewTopic("raw-events", 3, (short) 1);
	}
	
	@Bean
	public NewTopic processedEventsTopic() {
		return new NewTopic("processed-events", 3, (short) 1);
	}
	
	@Bean
	public NewTopic errorEventsTopic() {
		return new NewTopic("error-events", 1, (short) 1);
	}
}
