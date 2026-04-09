package com.meeting.springboot_meet.classroom.infrastructure.persistence.repository;

import com.meeting.springboot_meet.classroom.infrastructure.persistence.entity.ClassroomMemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SpringDataClassroomMemberRepository extends JpaRepository<ClassroomMemberEntity, Long> {
    Optional<ClassroomMemberEntity> findByClassroomIdAndUserId(Long classroomId, Long userId);
    List<ClassroomMemberEntity> findByClassroomId(Long classroomId);
    List<ClassroomMemberEntity> findByUserId(Long userId);
}
