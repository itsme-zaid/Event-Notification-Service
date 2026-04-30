package dev.zaid.event_notification_service.features.like;

import dev.zaid.event_notification_service.features.notification.Notification;
import dev.zaid.event_notification_service.features.notification.registry.NotificationMapperRegistry;
import dev.zaid.event_notification_service.features.notification.NotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class LikeEventConsumer {
    @Autowired
    private NotificationMapperRegistry mapperRegistry;
    @Autowired
    private NotificationService notificationService;
    private final static Logger log = LoggerFactory.getLogger(LikeService.class);
    @KafkaListener(topics = "like-events", groupId = "notification-group")
    public void consume(LikeEvent event) {
        // 1. Map event to notification
        Notification notification = mapperRegistry.map(event);

        // 2. Save to DB
        try{
            notificationService.save(notification);
        }catch(Exception e){
            log.warn("Kafka sending duplicate notifications");
            return;
        }

        // 3. (Optional) Send real-time push/email
    }
//    @KafkaListener(topics = "like-events", groupId = "notification-group")
//    public void consume(String message) {
//        System.out.println("=== CONSUMED === " + message);
//    }
}
