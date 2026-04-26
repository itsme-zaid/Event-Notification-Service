package dev.zaid.event_notification_service.mapper;

import dev.zaid.event_notification_service.dto.UserRequest;
import dev.zaid.event_notification_service.entity.User;
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
