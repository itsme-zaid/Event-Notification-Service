package dev.zaid.event_notification_service.features.post;

import dev.zaid.event_notification_service.features.post.Post;
import dev.zaid.event_notification_service.features.post.PostCustomRepo;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PostRepo extends MongoRepository<Post,String> , PostCustomRepo {
//    Optional<Post> findByUsername(String username);
//    void deleteByUsername(String username);
    //List<Optional<Post>> findByUsername(String username);
}
