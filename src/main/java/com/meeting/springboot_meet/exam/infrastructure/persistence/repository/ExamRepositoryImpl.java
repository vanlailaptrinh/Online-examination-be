package com.meeting.springboot_meet.exam.infrastructure.persistence.repository;

import com.meeting.springboot_meet.auth.infrastructure.persistence.entity.UserEntity;
import com.meeting.springboot_meet.auth.infrastructure.persistence.repository.SpringDataUserRepository;
import com.meeting.springboot_meet.exam.domain.model.Exam;
import com.meeting.springboot_meet.exam.domain.repository.ExamRepository;
import com.meeting.springboot_meet.exam.infrastructure.persistence.entity.ExamEntity;
import com.meeting.springboot_meet.exam.infrastructure.persistence.mapper.ExamMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class ExamRepositoryImpl implements ExamRepository {

    private final SpringDataExamRepository springDataExamRepository;
    private final SpringDataUserRepository springDataUserRepository;
    private final com.meeting.springboot_meet.classroom.infrastructure.persistence.repository.SpringDataClassroomRepository springDataClassroomRepository;
    private final SpringDataClassroomExamRepository springDataClassroomExamRepository;
    private final ExamMapper examMapper;

    @Override
    public Exam save(Exam exam) {
        ExamEntity entity = examMapper.toEntity(exam);
        
        if (exam.getTeacherId() != null) {
            UserEntity teacher = springDataUserRepository.findById(exam.getTeacherId())
                    .orElseThrow(() -> new RuntimeException("Teacher not found"));
            entity.setTeacher(teacher);
        }

        ExamEntity savedEntity = springDataExamRepository.save(entity);
        return examMapper.toDomain(savedEntity);
    }

    @Override
    public Optional<Exam> findById(Long id) {
        return springDataExamRepository.findById(id).map(examMapper::toDomain);
    }

    @Override
    public List<Exam> findByTeacherId(Long teacherId) {
        return springDataExamRepository.findByTeacherId(teacherId).stream()
                .map(examMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long id) {
        springDataExamRepository.deleteById(id);
    }

    @Override
    @org.springframework.transaction.annotation.Transactional
    public void assignToClassrooms(Long examId, List<Long> classroomIds, java.time.Instant start, java.time.Instant end, Integer duration) {
        ExamEntity exam = springDataExamRepository.findById(examId)
                .orElseThrow(() -> new RuntimeException("Exam not found"));

        for (Long classroomId : classroomIds) {
            com.meeting.springboot_meet.classroom.infrastructure.persistence.entity.ClassroomEntity classroom = 
                springDataClassroomRepository.findById(classroomId)
                .orElseThrow(() -> new RuntimeException("Classroom not found"));

            com.meeting.springboot_meet.exam.infrastructure.persistence.entity.ClassroomExamEntity assignment = 
                com.meeting.springboot_meet.exam.infrastructure.persistence.entity.ClassroomExamEntity.builder()
                .exam(exam)
                .classroom(classroom)
                .startTime(start)
                .endTime(end)
                .durationInMinutes(duration)
                .build();
            
            springDataClassroomExamRepository.save(assignment);
        }
    }

    @Override
    public List<Exam> findAssignedExamsByClassroomIds(List<Long> classroomIds) {
        if (classroomIds == null || classroomIds.isEmpty()) {
            return java.util.Collections.emptyList();
        }
        return springDataClassroomExamRepository.findByClassroomIdIn(classroomIds).stream()
                .map(assignment -> {
                    Exam exam = examMapper.toDomain(assignment.getExam());
                    // we can optionally set assignment specific metadata like StartTime/EndTime here,
                    // but for now relying on standard mapper mapped fields.
                    // The duration, start and end time are on the assignment.
                    // Oh, Exam domain model doesn't directly capture junction properties unless passed.
                    return exam;
                })
                .distinct() // distinct in case multiple class enrollments have the same exam
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Exam> findExamForStudent(Long examId, List<Long> classroomIds, boolean includeCorrectAnswers) {
        if (classroomIds == null || classroomIds.isEmpty()) return Optional.empty();
        
        List<com.meeting.springboot_meet.exam.infrastructure.persistence.entity.ClassroomExamEntity> assignments = 
                springDataClassroomExamRepository.findByClassroomIdIn(classroomIds).stream()
                .filter(a -> a.getExam().getId().equals(examId))
                .collect(Collectors.toList());

        if (assignments.isEmpty()) return Optional.empty();
        
        com.meeting.springboot_meet.exam.infrastructure.persistence.entity.ClassroomExamEntity assignment = assignments.get(0);
        Exam exam = examMapper.toDomain(assignment.getExam(), includeCorrectAnswers);
        exam.setDurationInMinutes(assignment.getDurationInMinutes());
        return Optional.of(exam);
    }
}
