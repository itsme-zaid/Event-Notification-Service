package dev.zaid.event_notification_service.service;

import dev.zaid.event_notification_service.events.DomainEvent;
import dev.zaid.event_notification_service.events.LikeEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class ProducerEvent {
    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;
    private static final String TOPIC = "like-events";
    public void produce(LikeEvent event) {
        // Key = postId for partitioning (optional but good)
        String key = event.getPostId();
        kafkaTemplate.send(TOPIC, key, event)
                .whenComplete((result, ex) -> {
                    if (ex != null) {
                        System.out.println("SEND FAILED: " + ex.getMessage());
                    } else {
                        System.out.println("PRODUCED: " + event.getEventId() +
                                " partition=" + result.getRecordMetadata().partition());
                    }
                });
    }
}
