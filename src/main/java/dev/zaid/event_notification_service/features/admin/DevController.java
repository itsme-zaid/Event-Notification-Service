package dev.zaid.event_notification_service.features.admin;

import dev.zaid.event_notification_service.features.notification.NotificationService;
import dev.zaid.event_notification_service.features.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
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
