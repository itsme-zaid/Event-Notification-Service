package dev.zaid.event_notification_service.features.post;

import dev.zaid.event_notification_service.events.DomainEvent;
import dev.zaid.event_notification_service.events.RetryEvent;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PostEventRE extends RetryEvent<PostEvent> {
    public PostEventRE(PostEvent orgEvent,int r, String errorMessage){
        super(orgEvent,r,errorMessage);
    }
}
