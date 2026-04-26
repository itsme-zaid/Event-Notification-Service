package dev.zaid.event_notification_service.dto.posts;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.springframework.data.mongodb.core.index.Indexed;

@Getter
@Setter
public class PostUpdate {
    private String title;
    private String content;
}
