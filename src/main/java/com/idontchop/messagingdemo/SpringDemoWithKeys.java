package com.idontchop.messagingdemo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class SpringDemoWithKeys {
	
	Logger logger = LoggerFactory.getLogger(SpringDemoWithKeys.class);
	
	@Autowired
	private KafkaTemplate<String, NotificationDto> kafkaTemplate;
	
	String topic = "TutorialTopic";
	String value = "Hello from Spring Demo With Keys";
	String key = "DemoWithKeys";
	String bootstrap = "staging:9092";
	
	public void run () {
		
		NotificationDto nt = new NotificationDto("TestUser").setFrom("Me").setType_id(10);
		Assert.state(kafkaTemplate!=null, "kafkaTemplate is null! wtf");
		kafkaTemplate.send(topic, nt);
	}

}
