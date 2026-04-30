package dev.zaid.event_notification_service.features.user.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserUpdate {
    private String username;
    private String password;
}
