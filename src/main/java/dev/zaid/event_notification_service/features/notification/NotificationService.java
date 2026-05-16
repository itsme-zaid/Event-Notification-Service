package dev.zaid.event_notification_service.features.notification;

import dev.zaid.event_notification_service.features.Jwt.CustomUserDetails;
import dev.zaid.event_notification_service.features.follow.FollowService;
import dev.zaid.event_notification_service.features.like.LikeService;
import dev.zaid.event_notification_service.features.notification.Notification;
import dev.zaid.event_notification_service.features.user.User;
import dev.zaid.event_notification_service.features.notification.NotificationRepo;
import dev.zaid.event_notification_service.features.post.PostRepo;
import dev.zaid.event_notification_service.features.user.UserRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NotificationService {
    @Autowired
    private NotificationRepo notificationRepo;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private PostRepo postRepo;
    @Autowired
    private FollowService followService;


    // generate Notification;
    public List<Notification> getAllUnread(Authentication authentication){
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        String userId = customUserDetails.getUserId();
        return notificationRepo.findUnreadByUserId(userId);
    }
    public void markAsRead(Authentication authentication){
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        String userId = customUserDetails.getUserId();
        notificationRepo.markAsRead(userId);
        return;
    }
    public void save(Notification notification){
        notificationRepo.save(notification);
    }
    public void saveForFollowers(Notification n){
        String userId = n.getUserId();

        List<String> followers = followService.getFollowers(userId);
        List<Notification> notifications = new ArrayList<>();

        for(String f : followers){
            notifications.add(
                    new Notification(n.getActorId(),f,n.getType(),n.getPostId(),n.getEventId())
            );
        }
        int batchsize = 300;
        for(int i=0; i<notifications.size(); i+=batchsize){
            List<Notification> batch =
                    notifications.subList(
                            i,
                            Math.min(i + batchsize, notifications.size())
                    );
            notificationRepo.saveAll(batch);
        }

    }
}
