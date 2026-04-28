package dev.zaid.event_notification_service.registry;

import dev.zaid.event_notification_service.entity.Notification;
import dev.zaid.event_notification_service.events.DomainEvent;
import dev.zaid.event_notification_service.mapper.event_mapper.NotificationMapper;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class NotificationMapperRegistry {
    private final Map<String,NotificationMapper> mapperMap = new HashMap<>();

//    Spring will automatically inject all the NotificationMappers implementation in this constructor all grouped together in a list<>
    public NotificationMapperRegistry(List<NotificationMapper> mappers){
        for (NotificationMapper e : mappers) mapperMap.put(e.getEventType(),e);
    }

    public Notification map(DomainEvent event){
        NotificationMapper mapper = mapperMap.get(event.getEventType());
        if(mapper == null) throw new RuntimeException("No mapper found in the mapper registry " + event.getEventType());
        return mapper.map(event);
    }
}
