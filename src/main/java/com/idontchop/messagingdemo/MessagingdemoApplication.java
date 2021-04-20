package com.idontchop.messagingdemo;

import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.kafka.support.serializer.JsonSerializer;

@SpringBootApplication
public class MessagingdemoApplication implements CommandLineRunner {
	
	static Logger logger = LoggerFactory.getLogger(MessagingdemoApplication.class);

	@Autowired
	private SpringDemoWithKeys dk;

	public static void main(String[] args) {
		SpringApplication.run(MessagingdemoApplication.class, args);
		

		
		Properties properties = new Properties();
		properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "staging:9092");
		properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
		properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class.getName());
		
		// create producer
		KafkaProducer<String, NotificationDto> producer = new KafkaProducer<>(properties);
		
		// record
		ProducerRecord<String, NotificationDto> record = new ProducerRecord<String,NotificationDto>("TutorialTopic", "Notification", new NotificationDto("TestUser").setType_id(3).setTo("Me"));
		
		// send
		producer.send(record, (recordMetaData, e) -> {
			
			logger.info("Received new metadata: " + recordMetaData.offset() + " - " + recordMetaData.timestamp());
		});
		
		producer.close();
		
		
		//Consumer c = new Consumer();
		//c.run();
	}
	
	@Override
	public void run(String... args) throws Exception {
		dk.run();
	}

}
