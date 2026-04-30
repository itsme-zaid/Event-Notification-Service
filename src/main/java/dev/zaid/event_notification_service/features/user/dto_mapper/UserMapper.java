package dev.zaid.event_notification_service.features.user.dto_mapper;

import dev.zaid.event_notification_service.features.user.dto.UserRequest;
import dev.zaid.event_notification_service.features.user.User;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class UserMapper {
    public User reqToUser(UserRequest userRequest){
        User user = new User();
        user.setUsername(userRequest.getUsername());
        user.setPassword(userRequest.getPassword());
        user.setRoles(List.of("USER"));
        return user;
    }
}
