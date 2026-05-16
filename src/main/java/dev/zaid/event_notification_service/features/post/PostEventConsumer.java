package dev.zaid.event_notification_service.features.post;

import dev.zaid.event_notification_service.features.like.LikeEvent;
import dev.zaid.event_notification_service.features.like.LikeService;
import dev.zaid.event_notification_service.features.notification.Notification;
import dev.zaid.event_notification_service.features.notification.NotificationService;
import dev.zaid.event_notification_service.features.notification.registry.NotificationMapperRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class PostEventConsumer {
    @Autowired
    private NotificationMapperRegistry mapperRegistry;
    @Autowired
    private NotificationService notificationService;
    private final static Logger log = LoggerFactory.getLogger(LikeService.class);
    @KafkaListener(topics = "post-events", groupId = "notification-group")
    public void consume(PostEvent event) {
        // 1. Map event to notification
        Notification notification = mapperRegistry.map(event);

        // 2. Save to DB
        try{
            notificationService.saveForFollowers(notification);
            System.out.println("=== CONSUMED === ");
        }catch(Exception e){
            log.warn("Kafka sending duplicate notifications");
            return;
        }

        // 3. (Optional) Send real-time push/email
    }
}
