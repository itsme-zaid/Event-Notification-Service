package dev.zaid.event_notification_service.features.follow.mapper;

import dev.zaid.event_notification_service.features.follow.Follow;
import dev.zaid.event_notification_service.features.follow.FollowEvent;
import dev.zaid.event_notification_service.features.like.LikeEvent;
import dev.zaid.event_notification_service.features.notification.Notification;
import dev.zaid.event_notification_service.features.notification.NotificationMapper;
import org.springframework.stereotype.Component;

@Component
public class FollowMapper implements NotificationMapper<FollowEvent> {
    @Override
    public String getEventType() {
        return "FOLLOW_EVENT";
    }

    @Override
    public Notification map(FollowEvent event) {
        Notification notification = new Notification();
        notification.setPostId(null);
        notification.setUserId(event.getUserId());
        notification.setActorId(event.getUserId());
        notification.setType(event.getEventType());
        notification.setEventTime(event.getTimestamp());
        return notification;
    }
}
