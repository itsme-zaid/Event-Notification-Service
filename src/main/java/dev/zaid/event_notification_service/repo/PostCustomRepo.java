package dev.zaid.event_notification_service.repo;

import dev.zaid.event_notification_service.entity.Post;

import java.util.List;

public interface PostCustomRepo {
    List<Post> getPostsByUserId(String userId);
}
