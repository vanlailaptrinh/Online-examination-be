package com.meeting.springboot_meet.classroom.infrastructure.persistence.mapper;

import com.meeting.springboot_meet.classroom.domain.model.Classroom;
import com.meeting.springboot_meet.classroom.infrastructure.persistence.entity.ClassroomEntity;
import com.meeting.springboot_meet.auth.infrastructure.persistence.entity.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class ClassroomMapper {

    public Classroom toDomain(ClassroomEntity entity) {
        if (entity == null) return null;
        return Classroom.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .teacherId(entity.getTeacher() != null ? entity.getTeacher().getId() : null)
                .createdAt(entity.getCreatedAt())
                .build();
    }

    public ClassroomEntity toEntity(Classroom domain) {
        if (domain == null) return null;
        return ClassroomEntity.builder()
                .id(domain.getId())
                .name(domain.getName())
                .description(domain.getDescription())
                // Note: Teacher UserEntity should be set in the repository/service 
                // by fetching it from the UserRepository
                .createdAt(domain.getCreatedAt())
                .build();
    }
}
