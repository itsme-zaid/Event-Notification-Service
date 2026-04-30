package dev.zaid.event_notification_service.features.post.dto;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.springframework.data.mongodb.core.index.Indexed;

@Getter
@Setter
public class PostRequest {
    @NonNull
    private String title;
    @NonNull
    private String content;
}
