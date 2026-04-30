package dev.zaid.event_notification_service.features.user;

import dev.zaid.event_notification_service.features.user.dto.UserRequest;
import dev.zaid.event_notification_service.features.user.dto.UserUpdate;
import dev.zaid.event_notification_service.features.user.dto_mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private UserMapper userMapper;
    private final PasswordEncoder pswdEncoder = new BCryptPasswordEncoder();
    public ResponseEntity<?> saveNewUser(UserRequest userRequest){
        User user = userMapper.reqToUser(userRequest);
        user.setPassword(pswdEncoder.encode(user.getPassword()));
        userRepo.save(user);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    public ResponseEntity<?> deleteUser(String username){
        userRepo.deleteByUsername(username);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    public ResponseEntity<?> updateUser(String username, UserUpdate userUpdate){
        User user = userRepo.findByUsername(username).orElseThrow();
        if(userUpdate.getUsername()!="") user.setUsername(userUpdate.getUsername());
        if(userUpdate.getPassword()!="") user.setPassword(pswdEncoder.encode(userUpdate.getPassword()));
        userRepo.save(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
