package dev.zaid.event_notification_service.features.notification;

import dev.zaid.event_notification_service.features.notification.Notification;

import java.util.List;

public interface NotificationCustomRepo {
    List<Notification> findUnreadByUserId(String userId);
    List<Notification> findByActorId(String actorId);
}
