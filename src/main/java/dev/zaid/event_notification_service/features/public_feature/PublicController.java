package dev.zaid.event_notification_service.features.public_feature;

import dev.zaid.event_notification_service.features.user.dto.UserRequest;
import dev.zaid.event_notification_service.features.user.User;
import dev.zaid.event_notification_service.features.user.UserDetailsServiceImpl;
import dev.zaid.event_notification_service.features.user.UserService;
import dev.zaid.event_notification_service.features.Jwt.JwtUtilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")
public class PublicController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private JwtUtilService jwtUtilService;
    @Autowired
    private UserService userService;
    @GetMapping("/health-check")
    public String healthCheck(){
        return "Working";
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody UserRequest userRequest){
        return userService.saveNewUser(userRequest);
    }
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user){
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getUsername(),user.getPassword())
        );
        if(auth.isAuthenticated()){
            UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUsername());
            return new ResponseEntity<>(jwtUtilService.generateToken(userDetails.getUsername()), HttpStatus.OK);
        }
        throw new RuntimeException("Invalid Credentials");
    }

}
