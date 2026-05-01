package dev.zaid.event_notification_service.features.like;

import com.mongodb.DuplicateKeyException;
import dev.zaid.event_notification_service.features.Jwt.CustomUserDetails;
import dev.zaid.event_notification_service.features.post.Post;
import dev.zaid.event_notification_service.features.user.User;
import dev.zaid.event_notification_service.features.post.PostRepo;
import dev.zaid.event_notification_service.features.user.UserRepo;
import dev.zaid.event_notification_service.producer.ProducerEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    private final static Logger log = LoggerFactory.getLogger(LikeService.class);
    public void postLike(Authentication authentication, String postId) {
        // user -> the one who's liking the post; THE ACTOR
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        // post.getUserId() the one who owns the posts (the notification should be delivered here)
        Post post = postRepo.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        Like like = new Like();
        like.setPostId(postId);
        like.setActorId(customUserDetails.getUserId());

        try {
            likeRepo.save(like); // idempotency via unique index
        } catch (Exception e) {
            log.warn("Duplicate like actorId={} postId={}", customUserDetails.getUserId(), postId);
            log.warn("Ayo you liking that post again : {} ", e.getMessage());
            return; // idempotent no-op
        }

        // produce only if new like actually inserted
        producerEvent.produce(new LikeEvent(customUserDetails.getUserId(), postId, post.getUserId()));
    }
    public ResponseEntity<?> unlike(Authentication authentication, String postId){
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        String userId = customUserDetails.getUserId();
        // a unique like con be obtained using both postId and actorId;
        Like like = likeRepo.findByPostIdAndActorId(postId,userId).orElseThrow(() -> new RuntimeException("Cannot unlike a non Liked post;"));
        likeRepo.deleteByPostIdAndActorId(postId,userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
