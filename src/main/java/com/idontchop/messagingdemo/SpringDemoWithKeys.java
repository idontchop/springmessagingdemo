package com.idontchop.messagingdemo;

import java.util.ArrayList;
import java.util.List;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.Header;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class SpringDemoWithKeys {
	
	Logger logger = LoggerFactory.getLogger(SpringDemoWithKeys.class);
	
	@Autowired
	private KafkaTemplate<String, NotificationDto> kafkaTemplate;
	
	String topic = "Notifications";
	String value = "Hello from Spring Demo With Keys";
	String key = "Notification";
	String bootstrap = "staging:9092";
	
	public void run () {
		
		
		NotificationDto nt = new NotificationDto("TestUser").setFrom("Me").setType_id(10);
		nt.setReferenceId("111");
		
		Message<NotificationDto> message = MessageBuilder
				.withPayload(nt)
				.setHeader(KafkaHeaders.TOPIC, topic)
				.setHeader(KafkaHeaders.MESSAGE_KEY, "Notification ID")
				.setHeader("event-type", "New Notification")
				.setHeader("test", "test value")
				.build();
		
        List<Header> headers = new ArrayList<>();
        headers.add(new RecordHeader("event-type","New Notification".getBytes()));

        List<Header> headersm = new ArrayList<>();
        headersm.add(new RecordHeader("event-type","Delete Notification".getBytes()));
        
        ProducerRecord<String, NotificationDto> nRecord = 
        		new ProducerRecord<>(topic, 0 , "111", nt, headers);
        
        ProducerRecord<String, NotificationDto> mRecord =
        		new ProducerRecord<>(topic, 0, "111", nt, headersm);

		Assert.state(kafkaTemplate!=null, "kafkaTemplate is null! wtf");
		kafkaTemplate.send(nRecord)
			.addCallback( 
					e -> logger.info("send success: " + e.getRecordMetadata().topic()),
					e -> logger.info("error"));
		
		kafkaTemplate.send(mRecord)
		.addCallback( 
				e -> logger.info("send success: " + e.getRecordMetadata().topic()),
				e -> logger.info("error"));
	logger.info("Sent message to Notifications topic.");
		logger.info("Sent message to Notifications topic.");
	}

}
