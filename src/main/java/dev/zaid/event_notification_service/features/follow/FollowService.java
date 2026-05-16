package dev.zaid.event_notification_service.features.follow;

import dev.zaid.event_notification_service.features.Jwt.CustomUserDetails;
import dev.zaid.event_notification_service.features.user.User;
import dev.zaid.event_notification_service.features.user.UserRepo;
import dev.zaid.event_notification_service.features.user.dto.UserResponse;
import dev.zaid.event_notification_service.features.user.dto_mapper.UserMapper;
import dev.zaid.event_notification_service.producer.ProducerEvent;
import lombok.Setter;
import org.apache.kafka.clients.producer.Producer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FollowService {
    @Autowired
    private FollowRepo followRepo;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private UserMapper userMapper;
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
    public ResponseEntity<?> getFollowing(Authentication authentication,int page, int size){
        CustomUserDetails user = (CustomUserDetails) authentication.getPrincipal();
        List<Follow> followList = followRepo.findByFollowerId(user.getUserId(),page,size);
        List<String> followingId = new ArrayList<>();
        for(Follow f : followList) followingId.add(f.getFollowingId());
        List<User> users = userRepo.getUsersById(followingId);
        List<UserResponse> userResponses = new ArrayList<>();
        for(User u : users) userResponses.add(userMapper.userToResp(u));
        return new ResponseEntity<>(userResponses,HttpStatus.OK);
    }
    public ResponseEntity<?> getFollowers(Authentication authentication,int page, int size){
        CustomUserDetails user = (CustomUserDetails) authentication.getPrincipal();
        List<Follow> followList = followRepo.findByFollowingId(user.getUserId(),page,size);
        List<String> followerIds = new ArrayList<>();
        for(Follow f : followList) followerIds.add(f.getFollowerId());
        List<User> users =userRepo.getUsersById(followerIds);
        List<UserResponse> userResponses = new ArrayList<>();
        for(User u : users) userResponses.add(userMapper.userToResp(u));
        return new ResponseEntity<>(userResponses,HttpStatus.OK);
    }

    public List<String> getFollowers(String actorId){
        // get all the follows with followerId -> userId;
        List<Follow> followList = followRepo.findByFollowingId(actorId).orElseThrow();
        List<String> followerIds = new ArrayList<>();
        for(Follow f : followList) followerIds.add(f.getFollowerId());
        return followerIds;
    }
}
