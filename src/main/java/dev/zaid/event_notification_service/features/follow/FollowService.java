package dev.zaid.event_notification_service.features.follow;

import dev.zaid.event_notification_service.features.Jwt.CustomUserDetails;
import dev.zaid.event_notification_service.producer.ProducerEvent;
import lombok.Setter;
import org.apache.kafka.clients.producer.Producer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class FollowService {
    @Autowired
    private FollowRepo followRepo;
    @Autowired
    private ProducerEvent producerEvent;
    public ResponseEntity<?> saveFollow(Authentication authentication, String followingId){
        Follow follow = new Follow();
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        follow.setFollowerId(customUserDetails.getUserId());
        follow.setFollowingId(followingId);
        try {
            followRepo.save(follow);
        } catch (Exception e) {
            System.out.println("Duplicate Follow ");
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        //generate event ; ; ;
        producerEvent.produceFollow(new FollowEvent(follow.getFollowerId(),followingId));
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
