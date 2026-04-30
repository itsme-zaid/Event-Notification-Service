package dev.zaid.event_notification_service.features.like;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Getter
@Setter
@CompoundIndexes({@CompoundIndex(
        name = "like_unique_for_each_post",
        def = "{'actorId':1,'postId':1}",
        unique = true
)})
@Document(collection = "likes") public class Like {
    @Id
    private String id;
    private String actorId;
    private String postId;
    private LocalDateTime createdAt = LocalDateTime.now();
}
