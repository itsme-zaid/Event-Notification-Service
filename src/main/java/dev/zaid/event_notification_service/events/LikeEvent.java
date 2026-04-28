package dev.zaid.event_notification_service.events;

import lombok.Getter;

@Getter public class LikeEvent extends DomainEvent{
    private final String actorId;
    private final String postId;
    private final String postOwnerId;
    protected LikeEvent(String actorId,String postId,String postOwnerId) {
        super("LIKE_EVENT");
        this.actorId = actorId;
        this.postId = postId;
        this.postOwnerId = postOwnerId;
    }
}
