package dev.zaid.event_notification_service.features.follow;

import dev.zaid.event_notification_service.features.user.UserController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class FollowCustomRepoImpl implements FollowCustomRepo{
    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public List<Follow> findByFollowingId(String userId, int page, int size) {
        Query query = new Query();
        query.addCriteria(Criteria.where("followingId").in(userId));
        query.with(Sort.by(Sort.Direction.DESC, "createdAt"));
        query.skip((long) page * size);
        query.limit(size);
        return mongoTemplate.find(query, Follow.class);
    }

    @Override
    public List<Follow> findByFollowerId(String userId, int page, int size) {
        Query query = new Query();
        query.addCriteria(Criteria.where("followerId").in(userId));
        query.with(Sort.by(Sort.Direction.DESC, "createdAt"));
        query.skip((long) page * size);
        query.limit(size);
        return mongoTemplate.find(query, Follow.class);
    }
}
