package dev.zaid.event_notification_service.features.follow;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/follow")
public class FollowController {
    @Autowired
    private FollowService followService;
    @PostMapping("/{userId}")
    public ResponseEntity<?> followUser(Authentication authentication, @PathVariable String userId){
        return followService.saveFollow(authentication,userId);
    }
    @GetMapping("/followers")
    public ResponseEntity<?> getFollowers(Authentication authentication,@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "20") int size){
        return followService.getFollowers(authentication,page,size);
    }
    @GetMapping("/following")
    public ResponseEntity<?> getFollowing(Authentication authentication, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "20") int size){
        return followService.getFollowing(authentication,page,size);
    }
}
