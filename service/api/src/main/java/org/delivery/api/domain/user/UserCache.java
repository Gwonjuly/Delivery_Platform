package org.delivery.api.domain.user;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.delivery.api.domain.user.model.User;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.time.Duration;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
@Slf4j
public class UserCache {

    private final RedisTemplate<String, User> userRedisTemplate;
    private final static Duration USER_CACHE_TTL = Duration.ofDays(5);
    private final ObjectMapper objectMapper;

    public void setUser(User user) {
        String key= getKey(user.getId());
        userRedisTemplate.opsForValue().set(key,user,USER_CACHE_TTL);
        log.info("Set user cache key: {}, {}", key, user);
    }

    public Optional<User> getUser(Long userId){
        User user = objectMapper.convertValue(userRedisTemplate.opsForValue().get(getKey(userId)), User.class);
        log.info("Get user cache key: {} {}", userId, user);
        return Optional.of(user);
    }

    private String getKey(Long userId){
        return "UID:" + userId;
    }

    private String serializeUser(User user) throws JsonProcessingException {
        return objectMapper.writeValueAsString(user);
    }
    private User deserializeUser(String json) throws JsonProcessingException {
        return objectMapper.readValue(json, User.class);
    }
}
