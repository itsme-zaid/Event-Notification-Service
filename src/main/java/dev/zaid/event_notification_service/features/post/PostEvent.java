package dev.zaid.event_notification_service.features.post;

import dev.zaid.event_notification_service.events.DomainEvent;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostEvent extends DomainEvent {
    private String authorId;
    private String postId;
    public PostEvent(String actorId, String postId){
        super("POST_EVENT");
        this.authorId = actorId;

    }
}
