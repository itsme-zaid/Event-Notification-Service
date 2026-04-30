package dev.zaid.event_notification_service.features.notification;

import dev.zaid.event_notification_service.features.notification.Notification;
import dev.zaid.event_notification_service.features.notification.NotificationCustomRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class NotificationCustomRepoImpl implements NotificationCustomRepo {
    @Autowired
    private MongoTemplate mongoTemplate;
    @Override
    public List<Notification> findUnreadByUserId(String userId) {
        Query query = new Query();
        query.addCriteria(
                Criteria.where("userId").is(userId)
                        .and("isRead").is(false)
        );
        return mongoTemplate.find(query,Notification.class);
    }

    @Override
    public List<Notification> findByActorId(String actorId) {
        Query query = new Query();
        query.addCriteria(
                Criteria.where("actorId").is(actorId)
        );
        return mongoTemplate.find(query,Notification.class);
    }
}
