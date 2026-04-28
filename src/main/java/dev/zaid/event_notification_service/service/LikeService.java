package dev.zaid.event_notification_service.service;

import dev.zaid.event_notification_service.entity.Like;
import dev.zaid.event_notification_service.entity.Post;
import dev.zaid.event_notification_service.entity.User;
import dev.zaid.event_notification_service.events.LikeEvent;
import dev.zaid.event_notification_service.repo.LikeRepo;
import dev.zaid.event_notification_service.repo.PostRepo;
import dev.zaid.event_notification_service.repo.UserRepo;
import org.apache.kafka.clients.consumer.internals.events.EventProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class LikeService {
    @Autowired
    private LikeRepo likeRepo;
    @Autowired
    private UserRepo userRepo;;
    @Autowired
    private PostRepo postRepo;

    @Autowired
    private ProducerEvent producerEvent;

    public ResponseEntity<?> postLike(String username, String postId){
        User user = userRepo.findByUsername(username).orElseThrow();
        Post post = postRepo.findById(postId).orElseThrow();
        Like like = new Like();
        like.setPostId(postId);
        like.setActorId(user.getId());
        likeRepo.save(like);
//         2. Produce event
        LikeEvent event = new LikeEvent(like.getActorId(), postId,post.getUserId());
        producerEvent.produce(event);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    public ResponseEntity<?> unlike(String username, String postId){
        User user = userRepo.findByUsername(username).orElseThrow();
        // a unique like con be obtained using both postId and actorId;
        Like like = likeRepo.findByPostIdAndActorId(postId,user.getId()).orElseThrow(() -> new RuntimeException("Cannot unlike a non Liked post;"));
        likeRepo.deleteByPostIdAndActorId(postId,user.getId());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
