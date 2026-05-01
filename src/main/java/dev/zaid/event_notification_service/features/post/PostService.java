package dev.zaid.event_notification_service.features.post;

import dev.zaid.event_notification_service.features.Jwt.CustomUserDetails;
import dev.zaid.event_notification_service.features.post.dto.PostRequest;
import dev.zaid.event_notification_service.features.post.dto.PostUpdate;
import dev.zaid.event_notification_service.features.user.User;
import dev.zaid.event_notification_service.features.post.dto_mapper.PostMapper;
import dev.zaid.event_notification_service.features.user.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class PostService {
    @Autowired
    private PostRepo postRepo;
    @Autowired
    private UserRepo userRepo;;
    @Autowired
    private PostMapper postMapper;
    public ResponseEntity<?> createPost(Authentication authentication, PostRequest postRequest){
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        String userId = customUserDetails.getUserId();
        Post post = postMapper.reqToPost(postRequest);
        post.setUserId(userId);
        postRepo.save(post);
        return new ResponseEntity<>(HttpStatus.OK);

    }
    public ResponseEntity<?> updatePost(PostUpdate postUpdate, Authentication authentication, String postId){
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        String userId = customUserDetails.getUserId();
        Post post = postRepo.findById(postId).orElseThrow();
        if(post.getUserId().equals(userId)){
            post = postMapper.reqUpdate(postUpdate,post);
            postRepo.save(post);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        System.out.println("Forbidden");
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    public ResponseEntity<?> deletePost(Authentication authentication,String postId){
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        String userId = customUserDetails.getUserId();
        Post post = postRepo.findById(postId).orElseThrow();
        if(post.getUserId().equals(userId)){
            postRepo.deleteById(postId);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    public ResponseEntity<?> getPost(Authentication authentication, String postId){
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        String userId = customUserDetails.getUserId();
        Post post = postRepo.findById(postId).orElseThrow();
        if(Objects.equals(post.getUserId(), userId))
            return new ResponseEntity<>(postMapper.postToResponse(post),HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    public List<Post> getPostsByUsername(Authentication authentication){
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        String userId = customUserDetails.getUserId();
        return postRepo.getPostsByUserId(userId);
    }

}
