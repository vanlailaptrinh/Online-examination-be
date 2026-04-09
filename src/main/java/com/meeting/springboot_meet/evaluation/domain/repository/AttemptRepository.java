package com.meeting.springboot_meet.evaluation.domain.repository;

import com.meeting.springboot_meet.evaluation.domain.model.Attempt;
import java.util.List;
import java.util.Optional;

public interface AttemptRepository {
    Attempt save(Attempt attempt);
    Optional<Attempt> findById(Long id);
    List<Attempt> findByStudentId(Long studentId);
    List<Attempt> findByExamId(Long examId);
    Optional<Attempt> findByStudentIdAndExamId(Long studentId, Long examId);
}
