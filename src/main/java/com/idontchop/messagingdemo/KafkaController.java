package com.idontchop.messagingdemo;

import java.io.IOException;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

@Service
public class KafkaController {
	

	
	Logger logger = LoggerFactory.getLogger(KafkaController.class);
	/*
	@KafkaListener(topics = "TutorialTopic",
			groupId = "NotificationService",
			properties = {ConsumerConfig.AUTO_OFFSET_RESET_CONFIG + ":earliest"},
			containerFactory = "filterKafkaListenerContainerFactory")
	public void consume(String message,
			@Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) String key) throws IOException {
		logger.info("Consumed: " + key + "- \"" + message + "\"");
		
	}*/

	@KafkaListener(topics = "TutorialTopic",
			groupId = "NotificationServiceDemo",
			properties = {ConsumerConfig.AUTO_OFFSET_RESET_CONFIG + ":latest"},
			containerFactory = "notificationKafkaListenerContainerFactory")
	public void consumeNotification(NotificationDto notification) throws IOException {
		logger.info("Consumed: " + "Notification" + "- \"" + notification.getFromId() + "\": " + notification.type_id);
	}
}
