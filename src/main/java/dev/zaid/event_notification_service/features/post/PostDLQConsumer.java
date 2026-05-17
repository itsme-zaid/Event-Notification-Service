package dev.zaid.event_notification_service.features.post;

import dev.zaid.event_notification_service.events.PoisonPill;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class PostDLQConsumer {
    private final static Logger log = LoggerFactory.getLogger(PostDLQConsumer.class);
    @KafkaListener(topics = "post-events-dlq", groupId = "notification-dlq-group")
    public void consume(PoisonPill<PostEvent> pill){
        log.error("""
        DLQ EVENT RECEIVED
        eventId={}
        eventType={}
        originalTopic={}
        failedAt={}
        errorMessage={}
        payload={}
        """,
                pill.getEventId(),
                pill.getEventType(),
                pill.getOriginalTopic(),
                pill.getFailedAt(),
                pill.getErrorMessage(),
                pill.getPayload()
        );
    }
}
