package dev.zaid.event_notification_service.features.user;

import java.util.List;

public interface UserCustomRepo {
    List<User> getUsersById(List<String> userId);
}
