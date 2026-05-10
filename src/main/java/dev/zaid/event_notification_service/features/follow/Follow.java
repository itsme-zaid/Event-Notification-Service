package dev.zaid.event_notification_service.features.follow;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "follows")
@Getter
@Setter
@CompoundIndex(
        // save notification only when their does not exist a prior notification with both the eventId and actorId same to this notification you are trying to generate;
        name = "unique_follow",
        def = "{'followerId':1,'followingId':1}",
        unique = true
)
public class Follow {
    @Id
    private String id;
    private String followerId;
    private String followingId;
    private LocalDateTime createdAt = LocalDateTime.now();
}
