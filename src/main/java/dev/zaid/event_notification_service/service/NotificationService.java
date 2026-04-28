package dev.zaid.event_notification_service.service;

import dev.zaid.event_notification_service.entity.Like;
import dev.zaid.event_notification_service.entity.Notification;
import dev.zaid.event_notification_service.entity.Post;
import dev.zaid.event_notification_service.entity.User;
import dev.zaid.event_notification_service.repo.NotificationRepo;
import dev.zaid.event_notification_service.repo.PostRepo;
import dev.zaid.event_notification_service.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationService {
    @Autowired
    private NotificationRepo notificationRepo;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private PostRepo postRepo;
    // generate Notification;
    public List<Notification> getAllUnread(String username){
        User user = userRepo.findByUsername(username).orElseThrow();
        String userId = user.getId();
        return notificationRepo.findUnreadByUserId(userId);
    }
    public void save(Notification notification){
        notificationRepo.save(notification);
    }

}
