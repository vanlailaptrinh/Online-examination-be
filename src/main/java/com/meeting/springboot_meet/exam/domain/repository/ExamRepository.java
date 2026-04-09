package com.meeting.springboot_meet.exam.domain.repository;

import com.meeting.springboot_meet.exam.domain.model.Exam;
import java.util.List;
import java.util.Optional;

public interface ExamRepository {
    Exam save(Exam exam);
    Optional<Exam> findById(Long id);
    List<Exam> findByTeacherId(Long teacherId);
    void deleteById(Long id);
    void assignToClassrooms(Long examId, List<Long> classroomIds, java.time.Instant start, java.time.Instant end, Integer duration);
    List<Exam> findAssignedExamsByClassroomIds(List<Long> classroomIds);
    Optional<Exam> findExamForStudent(Long examId, List<Long> classroomIds);
}
