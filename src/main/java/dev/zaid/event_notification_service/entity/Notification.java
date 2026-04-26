package dev.zaid.event_notification_service.entity;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Getter
@Setter
@CompoundIndex(
        // save notification only when their does not exist a prior notification with both the eventId and actorId same to this notification you are trying to generate;
        name = "event_recipient_unique",
        def = "{'eventId':1,'actorId':1}",
        unique = true
)
@Document(collection = "notifications") public class Notification {
    @Id
    private String id;
    private String actorId;
    private String userId;
    private String type;
    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime eventTime;
    private String eventId;
    private boolean isRead = false;
}
