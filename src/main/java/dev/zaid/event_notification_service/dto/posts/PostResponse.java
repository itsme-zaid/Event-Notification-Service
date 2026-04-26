package dev.zaid.event_notification_service.dto.posts;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class PostResponse {
    private LocalDateTime lastUpdated = LocalDateTime.now();
    private String title;
    private String content;
}
