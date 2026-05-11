package dev.zaid.event_notification_service.features.follow;

import java.util.List;
import java.util.Optional;

public interface FollowCustomRepo {
    List<Follow> findByFollowingId(String userId, int page, int size);
    List<Follow> findByFollowerId(String userId, int page, int size);
}
