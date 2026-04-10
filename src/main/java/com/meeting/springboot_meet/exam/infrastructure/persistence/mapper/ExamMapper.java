package com.meeting.springboot_meet.exam.infrastructure.persistence.mapper;

import com.meeting.springboot_meet.exam.domain.model.Exam;
import com.meeting.springboot_meet.exam.domain.model.Question;
import com.meeting.springboot_meet.exam.domain.model.Option;
import com.meeting.springboot_meet.exam.infrastructure.persistence.entity.ExamEntity;
import com.meeting.springboot_meet.exam.infrastructure.persistence.entity.QuestionEntity;
import com.meeting.springboot_meet.exam.infrastructure.persistence.entity.OptionEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.stream.Collectors;

@Component
public class ExamMapper {

    public Exam toDomain(ExamEntity entity) {
        return toDomain(entity, true);
    }

    public Exam toDomain(ExamEntity entity, boolean includeCorrectAnswers) {
        if (entity == null) return null;
        return Exam.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .description(entity.getDescription())
                .teacherId(entity.getTeacher() != null ? entity.getTeacher().getId() : null)
                .showResultToStudent(entity.isShowResultToStudent())
                .createdAt(entity.getCreatedAt())
                .questions(entity.getQuestions() != null ? entity.getQuestions().stream()
                        .map(q -> toQuestionDomain(q, includeCorrectAnswers))
                        .collect(Collectors.toList()) : new ArrayList<>())
                .build();
    }

    private Question toQuestionDomain(QuestionEntity entity, boolean includeCorrectAnswers) {
        return Question.builder()
                .id(entity.getId())
                .content(entity.getContent())
                .type(entity.getType().name())
                .examId(entity.getExam().getId())
                .options(entity.getOptions() != null ? entity.getOptions().stream()
                        .map(o -> toOptionDomain(o, includeCorrectAnswers))
                        .collect(Collectors.toList()) : new ArrayList<>())
                .build();
    }

    private Option toOptionDomain(OptionEntity entity, boolean includeCorrectAnswers) {
        return Option.builder()
                .id(entity.getId())
                .content(entity.getContent())
                .isCorrect(includeCorrectAnswers && entity.isCorrect())
                .questionId(entity.getQuestion().getId())
                .build();
    }

    public ExamEntity toEntity(Exam domain) {
        if (domain == null) return null;
        ExamEntity entity = ExamEntity.builder()
                .id(domain.getId())
                .title(domain.getTitle())
                .description(domain.getDescription())
                .showResultToStudent(domain.isShowResultToStudent())
                .createdAt(domain.getCreatedAt())
                .build();
        
        if (domain.getQuestions() != null) {
            entity.setQuestions(domain.getQuestions().stream()
                    .map(q -> toQuestionEntity(q, entity))
                    .collect(Collectors.toList()));
        }
        
        return entity;
    }

    private QuestionEntity toQuestionEntity(Question domain, ExamEntity examEntity) {
        QuestionEntity entity = QuestionEntity.builder()
                .id(domain.getId())
                .content(domain.getContent())
                .type(QuestionEntity.QuestionType.valueOf(domain.getType()))
                .exam(examEntity)
                .build();
        
        if (domain.getOptions() != null) {
            entity.setOptions(domain.getOptions().stream()
                    .map(o -> toOptionEntity(o, entity))
                    .collect(Collectors.toList()));
        }
        
        return entity;
    }

    private OptionEntity toOptionEntity(Option domain, QuestionEntity questionEntity) {
        return OptionEntity.builder()
                .id(domain.getId())
                .content(domain.getContent())
                .isCorrect(domain.isCorrect())
                .question(questionEntity)
                .build();
    }
}
