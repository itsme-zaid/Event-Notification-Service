package dev.zaid.event_notification_service.features.user;

import dev.zaid.event_notification_service.features.user.dto.UserUpdate;
import dev.zaid.event_notification_service.features.notification.Notification;
import dev.zaid.event_notification_service.features.notification.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private NotificationService notificationService;
    @DeleteMapping
    public ResponseEntity<?> deleteUser(Authentication authentication){
        String username = authentication.getName();
        return userService.deleteUser(username);
    }
    @PutMapping
    public ResponseEntity<?> updateUser(Authentication authentication, @RequestBody UserUpdate userUpdate){
        return userService.updateUser(authentication.getName(),userUpdate);
    }
    public List<Notification> getAllUnread(Authentication authentication){
        return notificationService.getAllUnread(authentication);
    }
}
