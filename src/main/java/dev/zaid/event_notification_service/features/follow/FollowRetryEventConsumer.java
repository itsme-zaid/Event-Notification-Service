package dev.zaid.event_notification_service.features.follow;

import com.mongodb.DuplicateKeyException;
import dev.zaid.event_notification_service.events.RetryEvent;
import dev.zaid.event_notification_service.features.like.LikeEvent;
import dev.zaid.event_notification_service.features.like.LikeRetryConsumer;
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
import tools.jackson.databind.ObjectMapper;

@Service
public class FollowRetryEventConsumer {
    @Autowired
    private NotificationService notificationService;
    @Autowired
    private NotificationMapperRegistry mapperRegistry;
    @Autowired
    private ProducerEvent producerEvent;
    @Autowired
    private ObjectMapper mapper;
    private final static Logger log = LoggerFactory.getLogger(FollowRetryEventConsumer.class);
    @KafkaListener(topics = "follow-events-re", groupId = "notification-group")
    public void consume(RetryEvent<FollowEvent> event) {
        FollowEvent followEvent = mapper.convertValue(event.getOriginalEvent(),FollowEvent.class);
        if(event.getRemainingRetries() == 0 ){
            log.info("Maximum retries consumed, adding to dlq");
            // add to dlq;
            return;
        }
        // 1. Map event to notification
        Notification notification = mapperRegistry.map(followEvent);
        // 2. Save to DB
        try{
            notificationService.save(notification);
            System.out.println("=== CONSUMED === ");
        }catch (DuplicateKeyException e){
            log.warn("Duplicate Event");
        }catch (TransientDataAccessException e){
            //retry
            event.setRemainingRetries(event.getRemainingRetries() - 1);
            producerEvent.produceFollowRE(event);
        }catch(Exception e){
            log.warn("Kafka sending duplicate notifications");
            return;
        }

        // 3. (Optional) Send real-time push/email
    }
}
