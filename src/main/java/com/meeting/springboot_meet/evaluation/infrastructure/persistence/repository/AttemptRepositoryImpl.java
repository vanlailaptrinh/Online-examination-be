package com.meeting.springboot_meet.evaluation.infrastructure.persistence.repository;

import com.meeting.springboot_meet.auth.infrastructure.persistence.entity.UserEntity;
import com.meeting.springboot_meet.auth.infrastructure.persistence.repository.SpringDataUserRepository;
import com.meeting.springboot_meet.evaluation.domain.model.Attempt;
import com.meeting.springboot_meet.evaluation.domain.repository.AttemptRepository;
import com.meeting.springboot_meet.evaluation.infrastructure.persistence.entity.AttemptEntity;
import com.meeting.springboot_meet.evaluation.infrastructure.persistence.entity.AnswerEntity;
import com.meeting.springboot_meet.evaluation.infrastructure.persistence.mapper.EvaluationMapper;
import com.meeting.springboot_meet.exam.infrastructure.persistence.entity.ExamEntity;
import com.meeting.springboot_meet.exam.infrastructure.persistence.entity.QuestionEntity;
import com.meeting.springboot_meet.exam.infrastructure.persistence.repository.SpringDataExamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class AttemptRepositoryImpl implements AttemptRepository {

    private final SpringDataAttemptRepository springDataAttemptRepository;
    private final SpringDataExamRepository springDataExamRepository;
    private final SpringDataUserRepository springDataUserRepository;
    private final EvaluationMapper evaluationMapper;

    @Override
    public Attempt save(Attempt attempt) {
        AttemptEntity entity = evaluationMapper.toEntity(attempt);

        if (attempt.getExamId() != null) {
            ExamEntity exam = springDataExamRepository.findById(attempt.getExamId())
                    .orElseThrow(() -> new RuntimeException("Exam not found"));
            entity.setExam(exam);
        }

        if (attempt.getStudentId() != null) {
            UserEntity student = springDataUserRepository.findById(attempt.getStudentId())
                    .orElseThrow(() -> new RuntimeException("Student not found"));
            entity.setStudent(student);
        }

        if (attempt.getAnswers() != null) {
          entity.setAnswers(attempt.getAnswers().stream().map(a -> {
              AnswerEntity answerEntity = new AnswerEntity();
              answerEntity.setAttempt(entity);
              answerEntity.setQuestion(entity.getExam().getQuestions().stream()
                  .filter(q -> q.getId().equals(a.getQuestionId()))
                  .findFirst().orElseThrow(() -> new RuntimeException("Question not found in exam")));
              answerEntity.setSelectedOptionIds(a.getSelectedOptionIds());
              return answerEntity;
          }).collect(Collectors.toList()));
        }

        AttemptEntity savedEntity = springDataAttemptRepository.save(entity);
        return evaluationMapper.toDomain(savedEntity);
    }

    @Override
    public Optional<Attempt> findById(Long id) {
        return springDataAttemptRepository.findById(id).map(evaluationMapper::toDomain);
    }

    @Override
    public List<Attempt> findByStudentId(Long studentId) {
        return springDataAttemptRepository.findByStudentId(studentId).stream()
                .map(evaluationMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Attempt> findByExamId(Long examId) {
        return springDataAttemptRepository.findByExamId(examId).stream()
                .map(evaluationMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Attempt> findByStudentIdAndExamId(Long studentId, Long examId) {
        return springDataAttemptRepository.findByStudentIdAndExamId(studentId, examId)
                .map(evaluationMapper::toDomain);
    }
}
