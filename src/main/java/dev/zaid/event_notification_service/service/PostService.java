package dev.zaid.event_notification_service.service;

import dev.zaid.event_notification_service.dto.posts.PostRequest;
import dev.zaid.event_notification_service.dto.posts.PostResponse;
import dev.zaid.event_notification_service.dto.posts.PostUpdate;
import dev.zaid.event_notification_service.entity.Post;
import dev.zaid.event_notification_service.entity.User;
import dev.zaid.event_notification_service.mapper.PostMapper;
import dev.zaid.event_notification_service.repo.PostRepo;
import dev.zaid.event_notification_service.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

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
    public ResponseEntity<?> createPost(String username, PostRequest postRequest){
        User user = userRepo.findByUsername(username).orElseThrow();
        Post post = postMapper.reqToPost(postRequest);
        post.setUserId(user.getId());
        postRepo.save(post);
        return new ResponseEntity<>(HttpStatus.OK);

    }
    public ResponseEntity<?> updatePost(PostUpdate postUpdate, String username, String postId){
        User user = userRepo.findByUsername(username).orElseThrow();
        Post post = postRepo.findById(postId).orElseThrow();
        if(post.getUserId().equals(user.getId())){
            post = postMapper.reqUpdate(postUpdate,post);
            postRepo.save(post);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        System.out.println("Forbidden");
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    public ResponseEntity<?> deletePost(String username,String postId){
        User user = userRepo.findByUsername(username).orElseThrow();
        Post post = postRepo.findById(postId).orElseThrow();
        if(post.getUserId().equals(user.getId())){
            postRepo.deleteById(postId);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    public ResponseEntity<?> getPost(String username, String postId){
        User user = userRepo.findByUsername(username).orElseThrow();
        Post post = postRepo.findById(postId).orElseThrow();
        if(Objects.equals(post.getUserId(), user.getId()))
            return new ResponseEntity<>(postMapper.postToResponse(post),HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    public List<Post> getPostsByUsername(String username){
        User user = userRepo.findByUsername(username).orElseThrow();
        String userId = user.getId();
        return postRepo.getPostsByUserId(userId);
    }

}
