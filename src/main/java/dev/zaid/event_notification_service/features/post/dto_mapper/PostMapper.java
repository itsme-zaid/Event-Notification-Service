package dev.zaid.event_notification_service.features.post.dto_mapper;

import dev.zaid.event_notification_service.features.post.dto.PostRequest;
import dev.zaid.event_notification_service.features.post.dto.PostResponse;
import dev.zaid.event_notification_service.features.post.dto.PostUpdate;
import dev.zaid.event_notification_service.features.post.Post;
import org.springframework.stereotype.Component;

@Component
public class PostMapper {
    public Post reqToPost(PostRequest postRequest){
        Post post = new Post();
        post.setTitle(postRequest.getTitle());
        post.setContent(postRequest.getContent());
        return post;
    }
    public Post reqUpdate(PostUpdate postUpdate, Post post){
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
