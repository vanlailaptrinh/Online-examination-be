package com.meeting.springboot_meet.evaluation.infrastructure.persistence.entity;

import com.meeting.springboot_meet.exam.infrastructure.persistence.entity.QuestionEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "exam_answers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AnswerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "attempt_id", nullable = false)
    private AttemptEntity attempt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id", nullable = false)
    private QuestionEntity question;

    @ElementCollection
    @CollectionTable(name = "answer_selected_options", joinColumns = @JoinColumn(name = "answer_id"))
    @Column(name = "option_id")
    @Builder.Default
    private Set<Long> selectedOptionIds = new HashSet<>();
}
