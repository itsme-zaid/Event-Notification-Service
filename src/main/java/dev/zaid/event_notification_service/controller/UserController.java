package dev.zaid.event_notification_service.controller;

import dev.zaid.event_notification_service.dto.UserUpdate;
import dev.zaid.event_notification_service.service.UserService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;
    @DeleteMapping
    public ResponseEntity<?> deleteUser(Authentication authentication){
        String username = authentication.getName();
        return userService.deleteUser(username);
    }
    @PutMapping
    public ResponseEntity<?> updateUser(Authentication authentication, @RequestBody UserUpdate userUpdate){
        return userService.updateUser(authentication.getName(),userUpdate);
    }
}
