package dev.zaid.event_notification_service.features.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import javax.swing.*;
import java.util.List;

@Repository
public class UserCustomRepoImpl implements UserCustomRepo{
    @Autowired
    private MongoTemplate mongoTemplate;
    @Override
    public List<User> getUsersById(List<String> userId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").in(userId));
        return mongoTemplate.find(query,User.class);
    }
}
