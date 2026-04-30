package dev.zaid.event_notification_service.features.post;

import dev.zaid.event_notification_service.features.post.Post;
import dev.zaid.event_notification_service.features.post.PostCustomRepo;
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
