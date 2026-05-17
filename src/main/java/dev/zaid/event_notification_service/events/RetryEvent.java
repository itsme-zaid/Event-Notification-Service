package dev.zaid.event_notification_service.events;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public abstract class RetryEvent<T> {
    private T originalEvent;
    private int remainingRetries;
    private String lastError;
    public RetryEvent(T originalEvent, int n, String lastError){
        this.originalEvent = originalEvent;
        this.lastError = lastError;
        this.remainingRetries = n;
    }

}
