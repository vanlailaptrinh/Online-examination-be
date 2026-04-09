package com.meeting.springboot_meet.auth.infrastructure.persistence.mapper;

import com.meeting.springboot_meet.auth.domain.model.User;
import com.meeting.springboot_meet.auth.infrastructure.persistence.entity.UserEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.stream.Collectors;

@Component
public class UserMapper {

    // Tránh vòng lặp, ta sẽ không map children tự động hai chiều
    public User toDomain(UserEntity entity) {
        if (entity == null) return null;
        return User.builder()
                .id(entity.getId())
                .email(entity.getEmail())
                .fullName(entity.getFullName())
                .enabled(entity.isEnabled())
                .createdAt(entity.getCreatedAt())
                .roles(entity.getRoles())
                .refreshTokens(new ArrayList<>())
                .userProvider(new ArrayList<>())
                .build();
    }

    public UserEntity toEntity(User domain) {
        if (domain == null) return null;
        return UserEntity.builder()
                .id(domain.getId())
                .email(domain.getEmail())
                .fullName(domain.getFullName())
                .enabled(domain.isEnabled())
                .createdAt(domain.getCreatedAt())
                .roles(domain.getRoles())
                .build();
    }
}
