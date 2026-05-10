package dev.zaid.event_notification_service.features.follow;

import dev.zaid.event_notification_service.events.DomainEvent;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FollowEvent extends DomainEvent {

    private String userId;
    private String followingId;

    public FollowEvent(String followerId, String followingId) {
        super("FOLLOW_EVENT");
        this.userId = followerId;
        this.followingId = followingId;
    }
}