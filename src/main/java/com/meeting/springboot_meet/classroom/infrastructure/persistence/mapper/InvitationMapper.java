package com.meeting.springboot_meet.classroom.infrastructure.persistence.mapper;

import com.meeting.springboot_meet.classroom.domain.model.Invitation;
import com.meeting.springboot_meet.classroom.infrastructure.persistence.entity.InvitationEntity;
import org.springframework.stereotype.Component;

@Component
public class InvitationMapper {

    public Invitation toDomain(InvitationEntity entity) {
        if (entity == null) return null;
        return Invitation.builder()
                .id(entity.getId())
                .email(entity.getEmail())
                .classroomId(entity.getClassroom() != null ? entity.getClassroom().getId() : null)
                .token(entity.getToken())
                .used(entity.isUsed())
                .createdAt(entity.getCreatedAt())
                .expiresAt(entity.getExpiresAt())
                .build();
    }

    public InvitationEntity toEntity(Invitation domain) {
        if (domain == null) return null;
        return InvitationEntity.builder()
                .id(domain.getId())
                .email(domain.getEmail())
                .token(domain.getToken())
                .used(domain.isUsed())
                .createdAt(domain.getCreatedAt())
                .expiresAt(domain.getExpiresAt())
                .build();
    }
}
