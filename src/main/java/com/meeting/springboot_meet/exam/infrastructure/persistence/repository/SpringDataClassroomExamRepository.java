package com.meeting.springboot_meet.exam.infrastructure.persistence.repository;

import com.meeting.springboot_meet.exam.infrastructure.persistence.entity.ClassroomExamEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SpringDataClassroomExamRepository extends JpaRepository<ClassroomExamEntity, Long> {
    List<ClassroomExamEntity> findByClassroomId(Long classroomId);
    List<ClassroomExamEntity> findByExamId(Long examId);
    List<ClassroomExamEntity> findByClassroomIdIn(List<Long> classroomIds);
}
