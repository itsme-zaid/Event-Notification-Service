package dev.zaid.event_notification_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

@SpringBootApplication
public class EventNotificationServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(EventNotificationServiceApplication.class, args);
	}
	@Bean
	public PlatformTransactionManager hehe(MongoDatabaseFactory dbfactory){
		return new MongoTransactionManager(dbfactory);
	}
}
