package dev.zaid.event_notification_service.query;

import dev.zaid.event_notification_service.entity.Post;
import dev.zaid.event_notification_service.repo.PostCustomRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PostCustomRepoImpl implements PostCustomRepo {
    @Autowired
    private MongoTemplate mongoTemplate;
    @Override
    public List<Post> getPostsByUserId(String userId) {
        Query query = new Query();
        query.addCriteria(
                Criteria.where("userId").is(userId)
        );
        return mongoTemplate.find(query,Post.class);
    }
}
