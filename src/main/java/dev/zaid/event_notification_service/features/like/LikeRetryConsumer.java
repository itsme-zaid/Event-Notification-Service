package dev.zaid.event_notification_service.features.like;

import com.mongodb.DuplicateKeyException;
import dev.zaid.event_notification_service.events.RetryEvent;
import dev.zaid.event_notification_service.features.notification.Notification;
import dev.zaid.event_notification_service.features.notification.NotificationService;
import dev.zaid.event_notification_service.features.notification.registry.NotificationMapperRegistry;
import dev.zaid.event_notification_service.features.post.PostEventConsumer;
import dev.zaid.event_notification_service.producer.ProducerEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.TransientDataAccessException;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class LikeRetryConsumer {
    @Autowired
    private NotificationService notificationService;
    @Autowired
    private NotificationMapperRegistry mapperRegistry;
    @Autowired
    private ProducerEvent producerEvent;
    private final static Logger log = LoggerFactory.getLogger(LikeRetryConsumer.class);
    @KafkaListener(topics = "like-events-re", groupId = "notification-group")
    public void consume(RetryEvent<LikeEvent> event) {
        if(event.getRemainingRetries() == 0 ){
            log.info("Maximum retries consumed, adding to dlq");
            // add to dlq;
            return;
        }
        // 1. Map event to notification
        Notification notification = mapperRegistry.map(event.getOriginalEvent());

        // 2. Save to DB
        try{
            notificationService.save(notification);
            System.out.println("=== CONSUMED === ");
        }catch (DuplicateKeyException e){
            log.warn("Duplicate Event");
        }catch (TransientDataAccessException e){

            //retry
            event.setRemainingRetries(event.getRemainingRetries() - 1);
            producerEvent.produceLikeRE(event);
        }catch(Exception e){
            log.warn("Kafka sending duplicate notifications");
            return;
        }

        // 3. (Optional) Send real-time push/email
    }
}
