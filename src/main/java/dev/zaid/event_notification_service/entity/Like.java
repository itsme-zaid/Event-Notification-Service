package dev.zaid.event_notification_service.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Getter
@Setter
@Document(collection = "likes") public class Like {
    @Id
    private String id;
    private String actorId;
    private String postId;
    private LocalDateTime createdAt = LocalDateTime.now();
}
