package com.idontchop.messagingdemo;

import java.time.Duration;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Consumer {
	
	Logger logger = LoggerFactory.getLogger(Consumer.class);
	
	public void run() {
		Properties properties = new Properties();
		properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "staging:9092");
		properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
		properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
		properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, "NotificationService");
		properties.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
		
		// consumer
		KafkaConsumer<String,String> consumer = new KafkaConsumer<>(properties);
		
		// subscribe
		// Can subscribe to multiple with list -- or Collection.singleton
		consumer.subscribe(List.of("TutorialTopic"));
		
		// poll
		consumer.poll(Duration.ofMillis(100))
			.forEach( record -> {
				logger.info("Record Received: " + record.key() + ":" + record.value() );
			});
		
		consumer.close();
	}

}
