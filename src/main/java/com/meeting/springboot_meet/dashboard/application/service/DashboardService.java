package com.meeting.springboot_meet.dashboard.application.service;

import com.meeting.springboot_meet.classroom.domain.model.Classroom;
import com.meeting.springboot_meet.classroom.domain.repository.ClassroomMemberRepository;
import com.meeting.springboot_meet.classroom.domain.repository.ClassroomRepository;
import com.meeting.springboot_meet.dashboard.application.dto.DashboardStats;
import com.meeting.springboot_meet.evaluation.domain.model.Attempt;
import com.meeting.springboot_meet.evaluation.domain.repository.AttemptRepository;
import com.meeting.springboot_meet.exam.domain.repository.ExamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final ClassroomRepository classroomRepository;
    private final ClassroomMemberRepository classroomMemberRepository;
    private final ExamRepository examRepository;
    private final AttemptRepository attemptRepository;

    public DashboardStats getStats(Long userId, boolean isTeacher) {
        if (isTeacher) {
            List<Classroom> classrooms = classroomRepository.findByTeacherId(userId);
            long totalClassrooms = classrooms.size();
            long totalExamsBuilt = examRepository.findByTeacherId(userId).size();
            
            // Sum up members from all teacher's classrooms
            long totalStudents = classrooms.stream()
                .mapToLong(c -> classroomMemberRepository.findByClassroomId(c.getId()).size())
                .sum();

            return DashboardStats.builder()
                .totalClassrooms(totalClassrooms)
                .totalExamsBuilt(totalExamsBuilt)
                .totalStudentsInClasses(totalStudents)
                .build();
        } else {
            List<Attempt> attempts = attemptRepository.findByStudentId(userId);
            long examsTaken = attempts.size();
            double averageScore = attempts.stream()
                .mapToDouble(Attempt::getScore)
                .average()
                .orElse(0.0);

            return DashboardStats.builder()
                .examsTaken(examsTaken)
                .averageScore(averageScore)
                .build();
        }
    }
}
