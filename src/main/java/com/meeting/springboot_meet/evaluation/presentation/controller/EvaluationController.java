package com.meeting.springboot_meet.evaluation.presentation.controller;

import com.meeting.springboot_meet.common.dto.response.ApiResponse;
import com.meeting.springboot_meet.evaluation.application.service.EvaluationService;
import com.meeting.springboot_meet.evaluation.domain.model.Attempt;
import com.meeting.springboot_meet.evaluation.domain.model.Answer;
import com.meeting.springboot_meet.evaluation.presentation.payload.ExamSubmitRequest;
import com.meeting.springboot_meet.security.service.UserDetailsImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/evaluations")
@RequiredArgsConstructor
public class EvaluationController {

    private final EvaluationService evaluationService;

    @PostMapping("/submit")
    @PreAuthorize("hasRole('STUDENT')")
    public ResponseEntity<ApiResponse<Attempt>> submitExam(
            @Valid @RequestBody ExamSubmitRequest request,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        
        List<Answer> answers = request.getAnswers().stream().map(aRequest -> 
            Answer.builder()
                .questionId(aRequest.getQuestionId())
                .selectedOptionIds(aRequest.getSelectedOptionIds())
                .build()
        ).collect(Collectors.toList());

        Attempt attempt = evaluationService.submitExam(userDetails.getId(), request.getExamId(), answers);
        return ResponseEntity.ok(new ApiResponse<>("Exam submitted successfully", attempt));
    }

    @GetMapping("/attempts")
    @PreAuthorize("hasRole('STUDENT')")
    public ResponseEntity<ApiResponse<List<Attempt>>> getMyAttempts(
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        
        List<Attempt> attempts = evaluationService.getStudentAttempts(userDetails.getId());
        return ResponseEntity.ok(new ApiResponse<>("Fetched attempts successfully", attempts));
    }

    @GetMapping("/exam/{examId}/attempts")
    @PreAuthorize("hasRole('TEACHER')")
    public ResponseEntity<ApiResponse<List<Attempt>>> getExamAttempts(@PathVariable Long examId) {
        List<Attempt> attempts = evaluationService.getClassAttempts(examId);
        return ResponseEntity.ok(new ApiResponse<>("Fetched exam attempts successfully", attempts));
    }

    @GetMapping("/exam/{examId}/my-attempt")
    @PreAuthorize("hasRole('STUDENT')")
    public ResponseEntity<ApiResponse<Attempt>> getMyAttempt(
            @PathVariable Long examId,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        
        return evaluationService.getStudentAttemptByExam(userDetails.getId(), examId)
                .map(attempt -> ResponseEntity.ok(new ApiResponse<>("Success", attempt)))
                .orElseGet(() -> ResponseEntity.ok(new ApiResponse<>("Not attempted", null)));
    }
}
