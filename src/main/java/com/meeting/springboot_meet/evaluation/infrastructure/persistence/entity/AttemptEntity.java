package com.meeting.springboot_meet.evaluation.infrastructure.persistence.entity;

import com.meeting.springboot_meet.auth.infrastructure.persistence.entity.UserEntity;
import com.meeting.springboot_meet.exam.infrastructure.persistence.entity.ExamEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "exam_attempts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AttemptEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "exam_id", nullable = false)
    private ExamEntity exam;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    private UserEntity student;

    @Builder.Default
    private Instant startTime = Instant.now();

    private Instant submitTime;

    private Double score;

    @OneToMany(mappedBy = "attempt", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<AnswerEntity> answers = new ArrayList<>();
}
