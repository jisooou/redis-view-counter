package com.counter.redis_view_counter.place.scheduler;

import com.counter.redis_view_counter.place.repository.PlaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class ViewCountScheduler {
    private static final String VIEW_KEY_PREFIX = "place:view:";

    private final StringRedisTemplate redisTemplate;
    private final PlaceRepository placeRepository;

    @Scheduled(fixedRate = 60000)
    public void syncViewCountToDatabase(){
        Set<String> keys = redisTemplate.keys(VIEW_KEY_PREFIX + "*");
        if (keys == null || keys.isEmpty()){
            return;
        }
        for (String key : keys){
            String value = redisTemplate.opsForValue().get(key);
            if (value == null){
                continue;
            }
            Long placeId = extractPlaceId(key);
            Long incrementCount = Long.parseLong(value);

            placeRepository.increaseViewCount(placeId, incrementCount);

            redisTemplate.delete(key);
        }
    }

    private Long extractPlaceId(String key){
        return Long.parseLong(key.substring(VIEW_KEY_PREFIX.length()));
    }
}
