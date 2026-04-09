package com.meeting.springboot_meet.classroom.infrastructure.persistence.mapper;

import com.meeting.springboot_meet.classroom.domain.model.ClassroomMember;
import com.meeting.springboot_meet.classroom.infrastructure.persistence.entity.ClassroomMemberEntity;
import org.springframework.stereotype.Component;

@Component
public class ClassroomMemberMapper {

    public ClassroomMember toDomain(ClassroomMemberEntity entity) {
        if (entity == null) return null;
        return ClassroomMember.builder()
                .id(entity.getId())
                .classroomId(entity.getClassroom() != null ? entity.getClassroom().getId() : null)
                .userId(entity.getUser() != null ? entity.getUser().getId() : null)
                .userName(entity.getUser() != null ? entity.getUser().getFullName() : null)
                .userEmail(entity.getUser() != null ? entity.getUser().getEmail() : null)
                .status(entity.getStatus().name())
                .joinedAt(entity.getJoinedAt())
                .build();
    }

    public ClassroomMemberEntity toEntity(ClassroomMember domain) {
        if (domain == null) return null;
        return ClassroomMemberEntity.builder()
                .id(domain.getId())
                .status(domain.getStatus() != null ? 
                    ClassroomMemberEntity.MemberStatus.valueOf(domain.getStatus()) : 
                    ClassroomMemberEntity.MemberStatus.PENDING)
                .joinedAt(domain.getJoinedAt())
                .build();
    }
}
