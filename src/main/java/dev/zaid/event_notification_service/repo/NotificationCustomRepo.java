package dev.zaid.event_notification_service.repo;

import dev.zaid.event_notification_service.entity.Notification;

import java.util.List;

public interface NotificationCustomRepo {
    List<Notification> findUnreadByUser(String userId);
    List<Notification> findByActorId(String actorId);
}
