package dev.zaid.event_notification_service.events;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;
@Getter
@NoArgsConstructor
@AllArgsConstructor
public abstract class DomainEvent {

    protected String eventId;
    protected String eventType;
    protected long timestamp;
    protected int version;

    protected DomainEvent(String eventType) {
        this.eventId = UUID.randomUUID().toString();
        this.eventType = eventType;
        this.timestamp = System.currentTimeMillis();
        this.version = 1;
    }
}