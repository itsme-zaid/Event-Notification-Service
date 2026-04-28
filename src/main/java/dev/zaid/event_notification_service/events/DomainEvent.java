package dev.zaid.event_notification_service.events;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;
@Getter
public abstract class DomainEvent {
    private final String eventId;
    private final String eventType;
    private final long timestamp;
    private final int version;


    protected DomainEvent(String eventType) {
        this.eventId = UUID.randomUUID().toString();
        this.eventType = eventType;
        this.timestamp = System.currentTimeMillis();
        this.version = 1;
    }

}
