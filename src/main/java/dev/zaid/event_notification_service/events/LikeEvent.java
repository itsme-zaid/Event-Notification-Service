package dev.zaid.event_notification_service.events;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LikeEvent extends DomainEvent {

    private String actorId;
    private String postId;
    private String postOwnerId;

    public LikeEvent(String actorId, String postId, String postOwnerId) {
        super("LIKE_EVENT");
        this.actorId = actorId;
        this.postId = postId;
        this.postOwnerId = postOwnerId;
    }
}