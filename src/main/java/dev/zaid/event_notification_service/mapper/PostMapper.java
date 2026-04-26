package dev.zaid.event_notification_service.mapper;

import dev.zaid.event_notification_service.dto.posts.PostRequest;
import dev.zaid.event_notification_service.dto.posts.PostResponse;
import dev.zaid.event_notification_service.dto.posts.PostUpdate;
import dev.zaid.event_notification_service.entity.Post;
import org.springframework.stereotype.Component;

@Component
public class PostMapper {
    public Post reqToPost(PostRequest postRequest){
        Post post = new Post();
        post.setTitle(postRequest.getTitle());
        post.setContent(postRequest.getContent());
        return post;
    }
    public Post reqUpdate(PostUpdate postUpdate){
        Post post = new Post();
        if(postUpdate.getTitle()!=null) post.setTitle(postUpdate.getTitle());
        if(postUpdate.getContent()!=null) post.setContent(postUpdate.getContent());
        return post;
    }
    public PostResponse postToResponse(Post post){
        PostResponse postResponse = new PostResponse();
        postResponse.setTitle(post.getTitle());
        postResponse.setContent(post.getContent());
        postResponse.setLastUpdated(post.getLastUpdated());
        return postResponse;
    }
}
