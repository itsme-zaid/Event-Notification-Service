package dev.zaid.event_notification_service.features.post.mapper;

import dev.zaid.event_notification_service.features.like.LikeEvent;
import dev.zaid.event_notification_service.features.notification.Notification;
import dev.zaid.event_notification_service.features.notification.NotificationMapper;
import dev.zaid.event_notification_service.features.post.PostEvent;
import org.springframework.stereotype.Component;

@Component
public class PostMapper implements NotificationMapper<PostEvent> {
    @Override
    public String getEventType() {
        return "POST_EVENT";
    }

    @Override
    public Notification map(PostEvent event) {
        Notification notification = new Notification();
        notification.setUserId(event.getAuthorId());
        notification.setType(event.getEventType());
        notification.setActorId(event.getAuthorId());
        notification.setEventId(event.getEventId());
        notification.setPostId(event.getPostId());
        return notification;
    }
}
