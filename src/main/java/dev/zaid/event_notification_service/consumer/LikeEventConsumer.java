package dev.zaid.event_notification_service.consumer;

import dev.zaid.event_notification_service.entity.Notification;
import dev.zaid.event_notification_service.events.LikeEvent;
import dev.zaid.event_notification_service.registry.NotificationMapperRegistry;
import dev.zaid.event_notification_service.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class LikeEventConsumer {
    @Autowired
    private NotificationMapperRegistry mapperRegistry;
    @Autowired
    private NotificationService notificationService;
    @KafkaListener(topics = "like-events", groupId = "notification-group")
    public void consume(LikeEvent event) {
        // 1. Map event to notification
        Notification notification = mapperRegistry.map(event);

        // 2. Save to DB
        notificationService.save(notification);

        // 3. (Optional) Send real-time push/email
    }
//    @KafkaListener(topics = "like-events", groupId = "notification-group")
//    public void consume(String message) {
//        System.out.println("=== CONSUMED === " + message);
//    }
}
