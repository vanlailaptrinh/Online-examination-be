package com.meeting.springboot_meet.evaluation.application.service;

import com.meeting.springboot_meet.evaluation.domain.model.Attempt;
import com.meeting.springboot_meet.evaluation.domain.model.Answer;
import com.meeting.springboot_meet.evaluation.domain.repository.AttemptRepository;
import com.meeting.springboot_meet.exam.domain.model.Exam;
import com.meeting.springboot_meet.exam.domain.model.Question;
import com.meeting.springboot_meet.exam.domain.model.Option;
import com.meeting.springboot_meet.exam.domain.repository.ExamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EvaluationService {

    private final AttemptRepository attemptRepository;
    private final ExamRepository examRepository;

    @Transactional
    public Attempt submitExam(Long userId, Long examId, List<Answer> answers) {
        if (attemptRepository.findByStudentIdAndExamId(userId, examId).isPresent()) {
            throw new RuntimeException("You have already submitted this exam.");
        }

        Exam exam = examRepository.findById(examId)
                .orElseThrow(() -> new RuntimeException("Exam not found"));

        double totalScoreAccumulator = 0.0;
        for (Question question : exam.getQuestions()) {
            Optional<Answer> studentAnswer = answers.stream()
                    .filter(a -> a.getQuestionId().equals(question.getId()))
                    .findFirst();

            if (studentAnswer.isPresent()) {
                totalScoreAccumulator += calculateQuestionScore(question, studentAnswer.get().getSelectedOptionIds());
            }
        }

        double finalScore = exam.getQuestions().isEmpty() ? 0.0 : (totalScoreAccumulator / exam.getQuestions().size()) * 10.0;

        Attempt attempt = Attempt.builder()
                .examId(examId)
                .studentId(userId)
                .startTime(Instant.now()) // Note: Should ideally be passed from frontend when they start
                .submitTime(Instant.now())
                .score(finalScore)
                .answers(answers)
                .build();

        return attemptRepository.save(attempt);
    }

    private double calculateQuestionScore(Question question, Set<Long> selectedOptionIds) {
        Set<Long> correctOptionIds = question.getOptions().stream()
                .filter(Option::isCorrect)
                .map(Option::getId)
                .collect(Collectors.toSet());

        long totalCorrectOptions = correctOptionIds.size();
        if (totalCorrectOptions == 0) return 0.0; // Avoid division by zero for bad data

        long correctChosen = selectedOptionIds.stream()
                .filter(correctOptionIds::contains)
                .count();

        long wrongChosen = selectedOptionIds.size() - correctChosen;

        // Rule 1: No wrong options chosen (just missed some correct ones) -> partial points
        if (wrongChosen == 0) {
            return (double) correctChosen / totalCorrectOptions;
        } 
        
        // Rule 2: Selected ALL correct options, but some wrong ones -> penalize for wrong options chosen
        if (correctChosen == totalCorrectOptions) {
            double score = (double) (correctChosen - wrongChosen) / totalCorrectOptions;
            return Math.max(0.0, score);
        }
        
        // Rule 3: Missed correct options AND chose wrong options -> 0 points
        return 0.0;
    }

    public List<Attempt> getStudentAttempts(Long userId) {
        return attemptRepository.findByStudentId(userId);
    }

    public List<Attempt> getClassAttempts(Long examId) {
        return attemptRepository.findByExamId(examId);
    }

    public Optional<Attempt> getStudentAttemptByExam(Long userId, Long examId) {
        return attemptRepository.findByStudentIdAndExamId(userId, examId);
    }
}
