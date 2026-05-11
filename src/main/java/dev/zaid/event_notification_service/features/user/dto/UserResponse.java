package dev.zaid.event_notification_service.features.user.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserResponse {
    private String username;
    private String id;
    private List<String> roles;

}
