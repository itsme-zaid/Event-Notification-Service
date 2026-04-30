package dev.zaid.event_notification_service.features.post;

import dev.zaid.event_notification_service.features.post.Post;

import java.util.List;

public interface PostCustomRepo {
    List<Post> getPostsByUserId(String userId);
}
