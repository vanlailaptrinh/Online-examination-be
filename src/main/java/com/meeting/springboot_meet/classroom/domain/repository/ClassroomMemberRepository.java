package com.meeting.springboot_meet.classroom.domain.repository;

import com.meeting.springboot_meet.classroom.domain.model.ClassroomMember;
import java.util.List;
import java.util.Optional;

public interface ClassroomMemberRepository {
    ClassroomMember save(ClassroomMember member);
    Optional<ClassroomMember> findByClassroomIdAndUserId(Long classroomId, Long userId);
    List<ClassroomMember> findByClassroomId(Long classroomId);
    List<ClassroomMember> findByUserId(Long userId);
    void deleteById(Long id);
}
