package dev.zaid.event_notification_service.features.notification;

import dev.zaid.event_notification_service.features.notification.Notification;
import dev.zaid.event_notification_service.features.notification.NotificationCustomRepo;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface NotificationRepo extends MongoRepository<Notification,String>, NotificationCustomRepo {

}
