package dev.zaid.event_notification_service.events;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
public class PoisonPill<T>{
    private T payload;
    private String originalTopic;
    private String errorMessage;
    private LocalDateTime failedAt;
    private String eventId;
    private String eventType;
    public PoisonPill(T payload, String originalTopic,String errorMessage,String eventId,String eventType){
        this.payload = payload;
        this.originalTopic = originalTopic;
        this.errorMessage = errorMessage;
        this.failedAt = LocalDateTime.now();
        this.eventId = eventId;
        this.eventType = eventType;
    }
}
