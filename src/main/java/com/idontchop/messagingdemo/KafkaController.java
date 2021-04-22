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
/*
	@KafkaListener(topics = "Notifications",
			groupId = "NotificationServiceDemo2",
			properties = {ConsumerConfig.AUTO_OFFSET_RESET_CONFIG + ":latest"},
			containerFactory = "notificationKafkaListenerContainerFactory")
	public void consumeNotification(NotificationDto notification,
			@Header("event-type") String eventType) throws IOException {
		logger.info("New Consumed " + eventType + ": " + "Notification" + "- \"" + notification.getFromId() + "\": " + notification.type_id);
	}*/
	
	@KafkaListener(topics = "Notifications",
			groupId = "NotificationServiceDemo2",
			properties = {ConsumerConfig.AUTO_OFFSET_RESET_CONFIG + ":latest"},
			containerFactory = "notification2KafkaListenerContainerFactory")
	public void consumemNotification(NotificationDto notification,
			@Header("event-type") String eventType) throws IOException {
		logger.info("Delete Consumed " + eventType + ": " + "Notification" + "- \"" + notification.getFromId() + "\": " + notification.type_id);
	}
}
