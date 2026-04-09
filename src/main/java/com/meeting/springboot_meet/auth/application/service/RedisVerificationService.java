package com.meeting.springboot_meet.auth.application.service;

import com.meeting.springboot_meet.auth.domain.model.UserRole;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class RedisVerificationService {
    private final RedisTemplate<String, String> redisTemplate;

    public RedisVerificationService(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void saveVerification(String token, String email, String hashedPassword, Set<UserRole> roles) {
        String rolesStr = roles != null 
            ? roles.stream().map(Enum::name).collect(Collectors.joining(","))
            : "";
        String value = email + ":" + hashedPassword + ":" + rolesStr;
        redisTemplate.opsForValue().set("VERIFICATION_TOKEN:" + token, value, 24, TimeUnit.HOURS);
    }


    public String getVerification(String token) {
        return redisTemplate.opsForValue().get("VERIFICATION_TOKEN:" + token);
    }

    public void deleteVerification(String token) {
        redisTemplate.delete("VERIFICATION_TOKEN:" + token);
    }
}

