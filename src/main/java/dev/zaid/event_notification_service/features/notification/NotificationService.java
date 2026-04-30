package dev.zaid.event_notification_service.features.notification;

import dev.zaid.event_notification_service.features.notification.Notification;
import dev.zaid.event_notification_service.features.user.User;
import dev.zaid.event_notification_service.features.notification.NotificationRepo;
import dev.zaid.event_notification_service.features.post.PostRepo;
import dev.zaid.event_notification_service.features.user.UserRepo;
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
