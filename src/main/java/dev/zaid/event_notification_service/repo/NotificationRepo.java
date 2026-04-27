package dev.zaid.event_notification_service.repo;

import dev.zaid.event_notification_service.entity.Notification;
import dev.zaid.event_notification_service.entity.User;
import dev.zaid.event_notification_service.query.NotificationCustomRepoImpl;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface NotificationRepo extends MongoRepository<Notification,String>,NotificationCustomRepo {

}
