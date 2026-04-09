package com.meeting.springboot_meet.exam.infrastructure.persistence.repository;

import com.meeting.springboot_meet.exam.infrastructure.persistence.entity.ExamEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SpringDataExamRepository extends JpaRepository<ExamEntity, Long> {
    List<ExamEntity> findByTeacherId(Long teacherId);
}
