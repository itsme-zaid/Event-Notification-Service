package dev.zaid.event_notification_service.entity;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Getter
@Setter
@Document(collection = "notifications") public class Notification {
    @Id
    private String id;
    private String actorId;
    private String userId;
    private String type;
    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime eventTime;
    private String evenId;
    private boolean isRead = false;
}
