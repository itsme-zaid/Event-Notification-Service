package dev.zaid.event_notification_service.features.user;

import dev.zaid.event_notification_service.features.user.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepo extends MongoRepository<User,String> {
    Optional<User> findByUsername(String username);
    void deleteByUsername(String username);
}
