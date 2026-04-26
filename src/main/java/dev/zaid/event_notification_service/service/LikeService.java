package dev.zaid.event_notification_service.service;

import dev.zaid.event_notification_service.entity.Like;
import dev.zaid.event_notification_service.entity.User;
import dev.zaid.event_notification_service.repo.LikeRepo;
import dev.zaid.event_notification_service.repo.UserRepo;
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


    public ResponseEntity<?> postLike(String username, String postId){
        User user = userRepo.findByUsername(username).orElseThrow();
        Like like = new Like();
        like.setPostId(postId);
        like.setActorId(user.getId());
        likeRepo.save(like);
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
