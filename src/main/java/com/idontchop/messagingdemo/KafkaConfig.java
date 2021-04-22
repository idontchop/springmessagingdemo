package com.idontchop.messagingdemo;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.header.Header;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.converter.DefaultJackson2JavaTypeMapper;
import org.springframework.kafka.support.converter.Jackson2JavaTypeMapper.TypePrecedence;
import org.springframework.kafka.support.converter.StringJsonMessageConverter;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

@EnableKafka
@Configuration
public class KafkaConfig {
	
	Logger logger = LoggerFactory.getLogger(KafkaConfig.class);
	
	@Value("${spring.kafka.consumer.bootstrap-servers}")
	private String bootstrapServers;
	
	@Bean
	public ConcurrentKafkaListenerContainerFactory<String, String>
	  filterKafkaListenerContainerFactory() {
		
		Map<String,Object> props = new HashMap<>();
		props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
		props.put(ConsumerConfig.GROUP_ID_CONFIG, "NotificationServiceDemo");
		props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		//ConsumerConfig.VALUE_DESERIALIZER_CLASS
		props.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
	    ConcurrentKafkaListenerContainerFactory<String, String> factory =
	      new ConcurrentKafkaListenerContainerFactory<>();
	    factory.setConsumerFactory(new DefaultKafkaConsumerFactory<String,String>(props));
	    factory.setRecordFilterStrategy( // filters out
	      record -> record.key()==null || !record.key().equals("DemoWithKeys") );
	    return factory;
	}
	
	@Bean
	public ConcurrentKafkaListenerContainerFactory<String, NotificationDto>
	  notificationKafkaListenerContainerFactory() {
		
		Map<String,Object> props = new HashMap<>();
		props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
		props.put(ConsumerConfig.GROUP_ID_CONFIG, "NotificationService");
		props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        props.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
        props.put(JsonDeserializer.USE_TYPE_INFO_HEADERS, "false");
        props.put(JsonDeserializer.VALUE_DEFAULT_TYPE, "com.idontchop.messagingdemo.NotificationDto");

	    ConcurrentKafkaListenerContainerFactory<String, NotificationDto> factory =
	      new ConcurrentKafkaListenerContainerFactory<>();
	    factory.setConsumerFactory(new DefaultKafkaConsumerFactory<String,NotificationDto>(props));
	    /*JsonDeserializer<NotificationDto> deserializer = new JsonDeserializer<>(NotificationDto.class);
	    deserializer.setRemoveTypeHeaders(false);
	    deserializer.addTrustedPackages("*");
	    deserializer.setUseTypeMapperForKey(true);
	    factory.setMessageConverter(deserializer);*/
	    factory.setRecordFilterStrategy( // filters out events for listener
	      record -> {
	    	  boolean hasEventType = false;
	    	  for (Header header : record.headers()) {
	    		  if(header.key().equals("event-type") &&
	    				  Arrays.equals(header.value(),"New Notification".getBytes()))
	    			  hasEventType = true;
		    	  if (hasEventType) logger.info(header.value().toString() + "New Notification".getBytes());

	    	  }
	      return record.key()==null || !hasEventType;
	      });
	    return factory;
	}
	
	@Bean
	public ConcurrentKafkaListenerContainerFactory<String, NotificationDto>
	  notification2KafkaListenerContainerFactory() {
		
		Map<String,Object> props = new HashMap<>();
		props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
		props.put(ConsumerConfig.GROUP_ID_CONFIG, "NotificationService");
		props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        props.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
        props.put(JsonDeserializer.USE_TYPE_INFO_HEADERS, "false");
        props.put(JsonDeserializer.VALUE_DEFAULT_TYPE, "com.idontchop.messagingdemo.NotificationDto");

	    ConcurrentKafkaListenerContainerFactory<String, NotificationDto> factory =
	      new ConcurrentKafkaListenerContainerFactory<>();
	    factory.setConsumerFactory(new DefaultKafkaConsumerFactory<String,NotificationDto>(props));
	    /*JsonDeserializer<NotificationDto> deserializer = new JsonDeserializer<>(NotificationDto.class);
	    deserializer.setRemoveTypeHeaders(false);
	    deserializer.addTrustedPackages("*");
	    deserializer.setUseTypeMapperForKey(true);
	    factory.setMessageConverter(deserializer);*/
	    factory.setRecordFilterStrategy( // filters out events for listener
	      record -> {
	    	  boolean hasEventType = false;
	    	  for (Header header : record.headers()) {
	    		  if(header.key().equals("event-type") &&
	    				  Arrays.equals(header.value(),"Delete Notification".getBytes()))
	    			  hasEventType = true;
	    	  }
	      return record.key()==null || !hasEventType;
	      });
	    return factory;
	}
	
	/*******************************
	 * Producer
	 * *****************************/
	
	
    @Bean
    public ProducerFactory<String, NotificationDto> producerFactory() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(
          ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, 
          bootstrapServers);
        configProps.put(
          ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, 
          StringSerializer.class);
        configProps.put(
          ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, 
          JsonSerializer.class);
        configProps.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, true);
        configProps.put(ProducerConfig.COMPRESSION_TYPE_CONFIG, "snappy");
        // idempotent helps guartantee data sent to kafka
        // compression is probably not needed for a while
        return new DefaultKafkaProducerFactory<>(configProps);
    }

    @Bean
    public KafkaTemplate<String, NotificationDto> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }
	 

}
