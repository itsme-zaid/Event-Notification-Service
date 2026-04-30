package dev.zaid.event_notification_service.features.post;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Getter
@Setter
@Document(collection = "posts") public class Post {
    @Id
    private String id;
    private String userId;
    private LocalDateTime lastUpdated = LocalDateTime.now();
    @NonNull
    private String title;
    @NonNull
    private String content;
}
