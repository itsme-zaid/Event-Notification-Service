package dev.zaid.event_notification_service.mapper.event_mapper;

import dev.zaid.event_notification_service.entity.Notification;
import dev.zaid.event_notification_service.events.DomainEvent;

public interface NotificationMapper <T extends DomainEvent> {
    String getEventType();
    Notification map(T event);
}
