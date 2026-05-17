package dev.zaid.event_notification_service.producer;

import dev.zaid.event_notification_service.events.PoisonPill;
import dev.zaid.event_notification_service.events.RetryEvent;
import dev.zaid.event_notification_service.features.follow.FollowEvent;
import dev.zaid.event_notification_service.features.post.PostEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service public class DLQProducer {
    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;
    public void producePost(PoisonPill<PostEvent> eventPoisonPill){
        kafkaTemplate.send("post-events-dlq",eventPoisonPill);
    }
}
