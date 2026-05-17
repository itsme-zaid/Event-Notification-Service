package dev.zaid.event_notification_service.features.post;

import com.mongodb.DuplicateKeyException;
import dev.zaid.event_notification_service.events.PoisonPill;
import dev.zaid.event_notification_service.events.RetryEvent;
import dev.zaid.event_notification_service.features.notification.Notification;
import dev.zaid.event_notification_service.features.notification.NotificationService;
import dev.zaid.event_notification_service.features.notification.registry.NotificationMapperRegistry;
import dev.zaid.event_notification_service.producer.DLQProducer;
import dev.zaid.event_notification_service.producer.ProducerEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.TransientDataAccessException;
import org.springframework.dao.TransientDataAccessResourceException;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

@Service
public class PostRetryEventConsumer {
    @Autowired
    private NotificationMapperRegistry mapperRegistry;
    @Autowired
    private NotificationService notificationService;

    private final static Logger log = LoggerFactory.getLogger(PostRetryEventConsumer.class);
    @Autowired
    private ProducerEvent producerEvent;
    @Autowired
    private DLQProducer dlqProducer;
    @Autowired
    private ObjectMapper mapper;
    @KafkaListener(topics = "post-events-re", groupId = "notification-group")
    public void consume(RetryEvent<PostEvent> event) {


        PostEvent postEvent = mapper.convertValue(event.getOriginalEvent(),PostEvent.class);
        if(event.getRemainingRetries() == 0 ){
            log.info("Maximum retries consumed, adding to dlq");
            // add to dlq;
            dlqProducer.producePost(new PoisonPill<>(postEvent,"post-events-re","Maximum Retries Done", postEvent.getEventId(), postEvent.getEventType()));
            return;
        }
        // 1. Map event to notification
        Notification notification = mapperRegistry.map(postEvent);

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
                   postEvent.getPostId(),
                    event.getRemainingRetries()
            );
            //send to retry topic
            event.setRemainingRetries(event.getRemainingRetries() - 1);
            producerEvent.producePostRE(event);
        }catch(Exception e){
            log.info("Something wrong");
            dlqProducer.producePost(new PoisonPill<>(postEvent,"post-events-re",e.getMessage(), postEvent.getEventId(), postEvent.getEventType()));
            // send to dlq;
        }

        // 3. (Optional) Send real-time push/email
    }
}
