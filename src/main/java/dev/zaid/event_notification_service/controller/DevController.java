package dev.zaid.event_notification_service.controller;

import dev.zaid.event_notification_service.entity.Like;
import dev.zaid.event_notification_service.entity.Notification;
import dev.zaid.event_notification_service.entity.User;
import dev.zaid.event_notification_service.service.NotificationService;
import dev.zaid.event_notification_service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dev")
public class DevController {
    @Autowired
    private NotificationService notificationService;
    @Autowired
    private UserService userService;


}
