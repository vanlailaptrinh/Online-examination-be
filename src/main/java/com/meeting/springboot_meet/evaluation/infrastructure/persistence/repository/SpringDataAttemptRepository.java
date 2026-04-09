package com.meeting.springboot_meet.evaluation.infrastructure.persistence.repository;

import com.meeting.springboot_meet.evaluation.infrastructure.persistence.entity.AttemptEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SpringDataAttemptRepository extends JpaRepository<AttemptEntity, Long> {
    List<AttemptEntity> findByStudentId(Long studentId);
    List<AttemptEntity> findByExamId(Long examId);
    java.util.Optional<AttemptEntity> findByStudentIdAndExamId(Long studentId, Long examId);
}
