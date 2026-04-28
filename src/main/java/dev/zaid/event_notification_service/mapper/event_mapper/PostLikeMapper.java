package dev.zaid.event_notification_service.mapper.event_mapper;

import dev.zaid.event_notification_service.entity.Notification;
import dev.zaid.event_notification_service.events.LikeEvent;
import org.springframework.stereotype.Component;

@Component
public class PostLikeMapper implements NotificationMapper<LikeEvent> {
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
        return notification;
    }
}
