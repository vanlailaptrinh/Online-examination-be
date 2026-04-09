package com.meeting.springboot_meet.exam.infrastructure.persistence.entity;

import com.meeting.springboot_meet.auth.infrastructure.persistence.entity.UserEntity;
import com.meeting.springboot_meet.classroom.infrastructure.persistence.entity.ClassroomEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "exams")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExamEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "teacher_id", nullable = false)
    private UserEntity teacher;

    @Builder.Default
    private boolean showResultToStudent = true;

    @Builder.Default
    private Instant createdAt = Instant.now();

    @OneToMany(mappedBy = "exam", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<QuestionEntity> questions = new ArrayList<>();
}
