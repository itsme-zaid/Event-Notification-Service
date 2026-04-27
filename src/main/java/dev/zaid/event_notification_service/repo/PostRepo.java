package dev.zaid.event_notification_service.repo;

import dev.zaid.event_notification_service.entity.Post;
import dev.zaid.event_notification_service.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface PostRepo extends MongoRepository<Post,String> ,PostCustomRepo{
//    Optional<Post> findByUsername(String username);
//    void deleteByUsername(String username);
    //List<Optional<Post>> findByUsername(String username);
}
