package dev.zaid.event_notification_service.features.follow;

import com.mongodb.DuplicateKeyException;
import dev.zaid.event_notification_service.events.RetryEvent;
import dev.zaid.event_notification_service.features.like.LikeEvent;
import dev.zaid.event_notification_service.features.like.LikeService;
import dev.zaid.event_notification_service.features.notification.Notification;
import dev.zaid.event_notification_service.features.notification.NotificationService;
import dev.zaid.event_notification_service.features.notification.registry.NotificationMapperRegistry;
import dev.zaid.event_notification_service.features.post.PostEvent;
import dev.zaid.event_notification_service.producer.ProducerEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.TransientDataAccessException;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class FollowEventConsumer {
    @Autowired
    private NotificationMapperRegistry mapperRegistry;
    @Autowired
    private NotificationService notificationService;

    @Autowired
    private ProducerEvent producerEvent;
    private final static Logger log = LoggerFactory.getLogger(LikeService.class);
    @KafkaListener(topics = "follow-events", groupId = "notification-group")
    public void consume(FollowEvent event) {
        // 1. Map event to notification
        Notification notification = mapperRegistry.map(event);

        // 2. Save to DB
        try{
            notificationService.save(notification);
        }catch(DuplicateKeyException e){
            log.warn("Kafka sending duplicate notifications");
        }catch(TransientDataAccessException e){
            //send to retry topic
            RetryEvent<FollowEvent> retryEvent = new RetryEvent<>(event,3,e.getMessage());
            producerEvent.produceFollowRE(retryEvent);
        }catch(Exception e){
            log.info("Something wrong");
            // send to dlq;
        }

        // 3. (Optional) Send real-time push/email
    }
//    @KafkaListener(topics = "like-events", groupId = "notification-group")
//    public void consume(String message) {
//        System.out.println("=== CONSUMED === " + message);
//    }
}
