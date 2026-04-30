package dev.zaid.event_notification_service.features.notification;

import dev.zaid.event_notification_service.features.notification.Notification;
import dev.zaid.event_notification_service.events.DomainEvent;

public interface NotificationMapper <T extends DomainEvent> {
    String getEventType();
    Notification map(T event);
}
