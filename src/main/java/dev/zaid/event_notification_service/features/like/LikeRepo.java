package dev.zaid.event_notification_service.features.like;

import dev.zaid.event_notification_service.features.like.Like;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface LikeRepo extends MongoRepository<Like,String> {
//    Optional<Like> findById(String username);
//    void deleteById(String username);
    Optional<Like> findByPostIdAndActorId(String postId, String userId);
    void deleteByPostIdAndActorId(String postId, String userId);
}
