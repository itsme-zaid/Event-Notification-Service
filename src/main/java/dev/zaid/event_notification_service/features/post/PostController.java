package dev.zaid.event_notification_service.features.post;

import dev.zaid.event_notification_service.features.post.dto.PostRequest;
import dev.zaid.event_notification_service.features.post.dto.PostUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posts")
public class PostController {
    // post -> like; delete -> unlike;
    @Autowired
    private PostService postService;
    @PostMapping
    public ResponseEntity<?> createPost(Authentication authentication, @RequestBody PostRequest postRequest){
        return postService.createPost(authentication.getName(),postRequest);
    }
    @GetMapping("/{postId}")
    public ResponseEntity<?> getPost(Authentication authentication,@PathVariable String postId){
        return postService.getPost(authentication.getName(),postId);
    }
    @DeleteMapping("/{postId}")
    public ResponseEntity<?> deletePost(Authentication authentication, @PathVariable String postId){
        return postService.deletePost(authentication.getName(),postId);
    }
    @PutMapping("/{postId}")
    public ResponseEntity<?> updatePost(@RequestBody PostUpdate postUpdate, Authentication authentication, @PathVariable String postId){
        return postService.updatePost(postUpdate, authentication.getName(), postId);
    }
    @GetMapping
    public List<Post> getPosts(Authentication authentication){
        return postService.getPostsByUsername(authentication.getName());
    }
}
