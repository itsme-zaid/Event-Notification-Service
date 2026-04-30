package dev.zaid.event_notification_service.features.like;

import dev.zaid.event_notification_service.features.like.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/likes")
public class LikeController {
    // post -> like; delete -> unlike;
    @Autowired
    private LikeService likeService;
    @PostMapping("/{postId}")
    public ResponseEntity<?> like(Authentication authentication, @PathVariable String postId){
        likeService.postLike(authentication.getName(),postId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @DeleteMapping("/{postId}")
    public ResponseEntity<?> unlike(Authentication authentication, @PathVariable String postId){
        return likeService.unlike(authentication.getName(),postId);
    }
}
