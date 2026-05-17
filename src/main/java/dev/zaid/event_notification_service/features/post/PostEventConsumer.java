package dev.zaid.event_notification_service.features.post;

import com.mongodb.DuplicateKeyException;
import dev.zaid.event_notification_service.events.RetryEvent;
import dev.zaid.event_notification_service.features.like.LikeEvent;
import dev.zaid.event_notification_service.features.like.LikeService;
import dev.zaid.event_notification_service.features.notification.Notification;
import dev.zaid.event_notification_service.features.notification.NotificationService;
import dev.zaid.event_notification_service.features.notification.registry.NotificationMapperRegistry;
import dev.zaid.event_notification_service.producer.ProducerEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.TransientDataAccessException;
import org.springframework.dao.TransientDataAccessResourceException;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class PostEventConsumer {
    @Autowired
    private NotificationMapperRegistry mapperRegistry;
    @Autowired
    private NotificationService notificationService;
    @Autowired
    private ProducerEvent producerEvent;
    private final static Logger log = LoggerFactory.getLogger(PostEventConsumer.class);
    @KafkaListener(topics = "post-events", groupId = "notification-group")
    public void consume(PostEvent event) {
        // 1. Map event to notification
        Notification notification = mapperRegistry.map(event);

        // 2. Save to DB
        try{
            //System.out.println("Sending a forced-retry");
            //throw new TransientDataAccessResourceException("Forced-Retry");
            notificationService.saveForFollowers(notification);
            System.out.println("=== CONSUMED === ");
        }catch(DuplicateKeyException e){
            log.warn("Kafka sending duplicate notifications");

        }catch(TransientDataAccessException e){
            //send to retry topic
            RetryEvent<PostEvent> retryEvent = new RetryEvent<>(event,3,e.getMessage());
            producerEvent.producePostRE(retryEvent);
        }catch(Exception e){
            log.info("Something wrong");
            // send to dlq;
        }

        // 3. (Optional) Send real-time push/email
    }
}
