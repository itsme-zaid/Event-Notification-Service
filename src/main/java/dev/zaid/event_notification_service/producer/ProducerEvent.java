package dev.zaid.event_notification_service.producer;

import dev.zaid.event_notification_service.events.RetryEvent;
import dev.zaid.event_notification_service.features.follow.FollowEvent;
import dev.zaid.event_notification_service.features.like.LikeEvent;
import dev.zaid.event_notification_service.features.post.PostEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

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
    ObjectMapper mapper = new ObjectMapper();
    public void producePost(PostEvent event){
        String key = event.getAuthorId();
        kafkaTemplate.send("post-events",event);
    }
    public void producePostRE(RetryEvent<PostEvent> re){
        kafkaTemplate.send("post-events-re",re);
    }
    public void produceLikeRE(RetryEvent<LikeEvent> re){
        kafkaTemplate.send("like-events-re",re);
    }
    public void produceFollowRE(RetryEvent<FollowEvent> re){
        kafkaTemplate.send("follow-events-re",re);
    }
}
