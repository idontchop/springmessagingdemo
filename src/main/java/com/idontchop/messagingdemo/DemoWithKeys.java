package com.idontchop.messagingdemo;

import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class DemoWithKeys {
	
	Logger logger = LoggerFactory.getLogger(DemoWithKeys.class);
	
	String topic = "TutorialTopic";
	String value = "Hello from Spring Demo With Keys";
	String key = "DemoWithKeys";
	String bootstrap = "staging:9092";
	

	
	public void run() {
		
		Properties properties = new Properties();
		properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "staging:9092");
		properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
		properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
		
		// create producer
		KafkaProducer<String, String> producer = new KafkaProducer<>(properties);
		
		// record
		ProducerRecord<String, String> record = new ProducerRecord<String,String>(topic, key, value);
		
		// send
		producer.send(record, (recordMetaData, e) -> {
			
			logger.info("Received new metadata: " +
					recordMetaData.offset() + " - " +
					"Partition: " + recordMetaData.partition());
		});
		
		producer.close();
	}

}
