package dev.zaid.event_notification_service.features.like;

import com.mongodb.DuplicateKeyException;
import dev.zaid.event_notification_service.events.RetryEvent;
import dev.zaid.event_notification_service.features.notification.Notification;
import dev.zaid.event_notification_service.features.notification.registry.NotificationMapperRegistry;
import dev.zaid.event_notification_service.features.notification.NotificationService;
import dev.zaid.event_notification_service.producer.ProducerEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.TransientDataAccessException;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class LikeEventConsumer {
    @Autowired
    private NotificationMapperRegistry mapperRegistry;
    @Autowired
    private NotificationService notificationService;
    @Autowired
    private ProducerEvent producerEvent;
    private final static Logger log = LoggerFactory.getLogger(LikeService.class);
    @KafkaListener(topics = "like-events", groupId = "notification-group")
    public void consume(LikeEvent event) {
        // 1. Map event to notification
        Notification notification = mapperRegistry.map(event);

        // 2. Save to DB
        try{
            notificationService.save(notification);
            System.out.println("=== CONSUMED === ");
        }catch (DuplicateKeyException e){
            log.warn("Duplicate Event");
        }catch (TransientDataAccessException e){
            //retry
            producerEvent.produceLikeRE(new RetryEvent<>(event,3,e.getMessage()));
        }catch(Exception e){
            log.warn("Kafka sending duplicate notifications");
            return;
        }
        // 3. (Optional) Send real-time push/email
    }

//    @KafkaListener(topics = "likes-event", groupId = "notification-group")
//    public void listen(String message) {
//        System.out.println("Received: " + message);
//    }
//    @KafkaListener(topics = "like-events", groupId = "notification-group")
//    public void consume(String message) {
//        System.out.println("=== CONSUMED === " + message);
//    }
}
