package dev.zaid.event_notification_service.features.post;

import com.mongodb.DuplicateKeyException;
import dev.zaid.event_notification_service.events.RetryEvent;
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
public class PostRetryEventConsumer {
    @Autowired
    private NotificationMapperRegistry mapperRegistry;
    @Autowired
    private NotificationService notificationService;

    private final static Logger log = LoggerFactory.getLogger(PostRetryEventConsumer.class);
    @Autowired
    private ProducerEvent producerEvent;
    @KafkaListener(topics = "post-events-re", groupId = "notification-group")
    public void consume(RetryEvent<PostEvent> event) {
        if(event.getRemainingRetries() == 0 ){
            log.info("Maximum retries consumed, adding to dlq");
            // add to dlq;
            return;
        }
        // 1. Map event to notification
        Notification notification = mapperRegistry.map(event.getOriginalEvent());

        // 2. Save to DB
        try{
            //throw new TransientDataAccessResourceException("Forced retry at PostRetryEventConsumer");
            notificationService.saveForFollowers(notification);
            System.out.println("=== CONSUMED === at PostRetryEventConsumer ");
        }catch(DuplicateKeyException e){
            log.warn("Kafka sending duplicate notifications");
        }catch(TransientDataAccessException e){
            log.warn(
                    "Retrying post event postId={} retriesLeft={}",
                    event.getOriginalEvent().getPostId(),
                    event.getRemainingRetries()
            );
            //send to retry topic
            event.setRemainingRetries(event.getRemainingRetries() - 1);
            producerEvent.producePostRE(event);
        }catch(Exception e){
            log.info("Something wrong");
            // send to dlq;
        }

        // 3. (Optional) Send real-time push/email
    }
}
