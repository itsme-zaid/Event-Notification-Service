package dev.zaid.event_notification_service.producer;

import dev.zaid.event_notification_service.features.follow.FollowEvent;
import dev.zaid.event_notification_service.features.like.LikeEvent;
import dev.zaid.event_notification_service.features.post.PostEvent;
import dev.zaid.event_notification_service.features.post.PostEventRE;
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
    public void produceFollow(FollowEvent event){
        String key = event.getUserId();
        kafkaTemplate.send("follow-events",key,event);
    }

    public void producePost(PostEvent event){
        String key = event.getAuthorId();
        kafkaTemplate.send("post-events",event);
    }
    public void producePostRE(PostEventRE event){
        String key = event.getOriginalEvent().getAuthorId();
        kafkaTemplate.send("post-events-re",event);
    }
}
