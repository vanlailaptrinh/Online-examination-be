package com.meeting.springboot_meet.exam.application.service;

import com.meeting.springboot_meet.classroom.domain.model.ClassroomMember;
import com.meeting.springboot_meet.classroom.domain.repository.ClassroomMemberRepository;
import com.meeting.springboot_meet.exam.domain.model.Exam;
import com.meeting.springboot_meet.exam.domain.repository.ExamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ExamService {

    private final ExamRepository examRepository;
    private final ClassroomMemberRepository classroomMemberRepository;

    @Transactional
    public Exam createExam(Exam exam, Long teacherId) {
        exam.setTeacherId(teacherId);
        exam.setCreatedAt(Instant.now());
        return examRepository.save(exam);
    }

    public List<Exam> getTeacherExams(Long teacherId) {
        return examRepository.findByTeacherId(teacherId);
    }

    public Optional<Exam> getExamById(Long id) {
        return examRepository.findById(id);
    }

    public Optional<Exam> getExamForStudent(Long examId, Long studentId) {
        List<Long> classroomIds = classroomMemberRepository.findByUserId(studentId).stream()
            .map(ClassroomMember::getClassroomId)
            .toList();
        return examRepository.findExamForStudent(examId, classroomIds);
    }

    public List<Exam> getStudentExams(Long studentId) {
        List<Long> classroomIds = classroomMemberRepository.findByUserId(studentId).stream()
            .map(ClassroomMember::getClassroomId)
            .toList();

        return examRepository.findAssignedExamsByClassroomIds(classroomIds);
    }

    @Transactional
    public void deleteExam(Long id) {
        examRepository.deleteById(id);
    }

    @Transactional
    public void assignExamToClassrooms(Long examId, List<Long> classroomIds, java.time.Instant start, java.time.Instant end, Integer duration) {
        examRepository.assignToClassrooms(examId, classroomIds, start, end, duration);
    }

    @Transactional
    public void updateShowResultToStudent(Long id, boolean showResult) {
        Exam exam = examRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Exam not found"));
        exam.setShowResultToStudent(showResult);
        examRepository.save(exam);
    }
}
