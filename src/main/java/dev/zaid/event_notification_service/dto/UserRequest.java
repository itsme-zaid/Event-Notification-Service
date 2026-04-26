package dev.zaid.event_notification_service.dto;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.springframework.data.mongodb.core.index.Indexed;

@Getter
@Setter
public class UserRequest {
    @NonNull
    @Indexed(unique = true)
    private String username;
    @NonNull
    private String password;
}
