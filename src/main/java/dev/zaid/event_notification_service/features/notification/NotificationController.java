package dev.zaid.event_notification_service.features.notification;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/notifications")
public class NotificationController {
    @Autowired
    private NotificationService notificationService;

    @GetMapping("/get-unread")
    public List<Notification> getUnread(Authentication authentication){
        return notificationService.getAllUnread(authentication);
    }
    @PostMapping("/mark-as-read")
    public ResponseEntity<?> markAsRead(Authentication authentication){
        notificationService.markAsRead(authentication);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
