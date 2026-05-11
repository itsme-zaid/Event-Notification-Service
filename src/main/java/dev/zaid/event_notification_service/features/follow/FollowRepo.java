package dev.zaid.event_notification_service.features.follow;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface FollowRepo extends MongoRepository<Follow,String>,FollowCustomRepo{


}
