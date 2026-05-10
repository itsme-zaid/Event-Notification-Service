package dev.zaid.event_notification_service.features.follow;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface FollowRepo extends MongoRepository<Follow,String> {
}
