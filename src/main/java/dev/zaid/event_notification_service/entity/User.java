package dev.zaid.event_notification_service.entity;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Document(collection = "users")
public class User {
    @NonNull
    @Indexed(unique = true)
    private String username;
    @NonNull
    private String password;
    private LocalDateTime toc = LocalDateTime.now();
    @Id
    private String id;
    private List<String> roles = new ArrayList<>();
}
