package com.meeting.springboot_meet.evaluation.infrastructure.persistence.mapper;

import com.meeting.springboot_meet.evaluation.domain.model.Attempt;
import com.meeting.springboot_meet.evaluation.domain.model.Answer;
import com.meeting.springboot_meet.evaluation.infrastructure.persistence.entity.AttemptEntity;
import com.meeting.springboot_meet.evaluation.infrastructure.persistence.entity.AnswerEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.stream.Collectors;

@Component
public class EvaluationMapper {

    public Attempt toDomain(AttemptEntity entity) {
        if (entity == null) return null;
        return Attempt.builder()
                .id(entity.getId())
                .examId(entity.getExam().getId())
                .examTitle(entity.getExam().getTitle())
                .studentId(entity.getStudent().getId())
                .startTime(entity.getStartTime())
                .submitTime(entity.getSubmitTime())
                .score(entity.getScore())
                .answers(entity.getAnswers() != null ? entity.getAnswers().stream()
                        .map(this::toAnswerDomain)
                        .collect(Collectors.toList()) : new ArrayList<>())
                .build();
    }

    private Answer toAnswerDomain(AnswerEntity entity) {
        return Answer.builder()
                .id(entity.getId())
                .attemptId(entity.getAttempt().getId())
                .questionId(entity.getQuestion().getId())
                .selectedOptionIds(entity.getSelectedOptionIds())
                .build();
    }

    public AttemptEntity toEntity(Attempt domain) {
        if (domain == null) return null;
        return AttemptEntity.builder()
                .id(domain.getId())
                .startTime(domain.getStartTime())
                .submitTime(domain.getSubmitTime())
                .score(domain.getScore())
                .build();
    }
}
