package dev.zaid.event_notification_service.features.like.mapper;

import dev.zaid.event_notification_service.features.notification.Notification;
import dev.zaid.event_notification_service.features.like.LikeEvent;
import dev.zaid.event_notification_service.features.notification.NotificationMapper;
import org.springframework.stereotype.Component;

@Component
public class LikeMapper implements NotificationMapper<LikeEvent> {
    @Override
    public String getEventType() {
        return "LIKE_EVENT";
    }

    @Override
    public Notification map(LikeEvent event) {
        Notification notification = new Notification();
        notification.setUserId(event.getPostOwnerId());
        notification.setType(event.getEventType());
        notification.setActorId(event.getActorId());
        notification.setEventId(event.getEventId());
        notification.setPostId(event.getPostId());
        return notification;
    }
}
