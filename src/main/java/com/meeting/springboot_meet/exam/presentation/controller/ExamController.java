package com.meeting.springboot_meet.exam.presentation.controller;

import com.meeting.springboot_meet.common.dto.response.ApiResponse;
import com.meeting.springboot_meet.exam.application.service.ExamService;
import com.meeting.springboot_meet.exam.domain.model.Exam;
import com.meeting.springboot_meet.exam.domain.model.Question;
import com.meeting.springboot_meet.exam.domain.model.Option;
import com.meeting.springboot_meet.exam.presentation.payload.ExamCreateRequest;
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
@RequestMapping("/api/exams")
@RequiredArgsConstructor
public class ExamController {

    private final ExamService examService;

    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_TEACHER')")
    public ResponseEntity<ApiResponse<Exam>> createExam(
            @Valid @RequestBody ExamCreateRequest request,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        
        Exam exam = Exam.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .showResultToStudent(request.isShowResultToStudent())
                .questions(request.getQuestions().stream().map(qRequest -> 
                    Question.builder()
                        .content(qRequest.getContent())
                        .type(qRequest.getType())
                        .options(qRequest.getOptions().stream().map(oRequest -> {
                            System.out.println("[CREATE EXAM] option=" + oRequest.getContent() + " isCorrect=" + oRequest.isCorrect());
                            return Option.builder()
                                .content(oRequest.getContent())
                                .isCorrect(oRequest.isCorrect())
                                .build();
                        }).collect(Collectors.toList()))
                        .build()
                ).collect(Collectors.toList()))
                .build();

        Exam createdExam = examService.createExam(exam, userDetails.getId());
        return ResponseEntity.ok(new ApiResponse<>("Exam created successfully", createdExam));
    }

    @GetMapping("/teacher")
    @PreAuthorize("hasAuthority('ROLE_TEACHER')")
    public ResponseEntity<ApiResponse<List<Exam>>> getTeacherExams(
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        
        List<Exam> exams = examService.getTeacherExams(userDetails.getId());
        return ResponseEntity.ok(new ApiResponse<>("Fetched exams successfully", exams));
    }

    @GetMapping("/student")
    @PreAuthorize("hasAuthority('ROLE_STUDENT')")
    public ResponseEntity<ApiResponse<List<Exam>>> getStudentExams(
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        
        List<Exam> exams = examService.getStudentExams(userDetails.getId());
        return ResponseEntity.ok(new ApiResponse<>("Fetched exams successfully", exams));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Exam>> getExam(
            @PathVariable Long id,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        
        if (userDetails != null && userDetails.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_STUDENT"))) {
            return examService.getExamForStudent(id, userDetails.getId())
                    .map(exam -> ResponseEntity.ok(new ApiResponse<>("Fetched student exam context", exam)))
                    .orElse(ResponseEntity.notFound().build());
        }

        return examService.getExamById(id)
                .map(exam -> ResponseEntity.ok(new ApiResponse<>("Fetched exam successfully", exam)))
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_TEACHER')")
    public ResponseEntity<ApiResponse<Void>> deleteExam(@PathVariable Long id) {
        examService.deleteExam(id);
        return ResponseEntity.ok(new ApiResponse<>("Exam deleted successfully", null));
    }

    @PostMapping("/{id}/assignments")
    @PreAuthorize("hasAuthority('ROLE_TEACHER')")
    public ResponseEntity<ApiResponse<Void>> assignExam(
            @PathVariable Long id,
            @Valid @RequestBody com.meeting.springboot_meet.exam.presentation.payload.AssignmentRequest request) {
        
        examService.assignExamToClassrooms(id, request.getClassroomIds(), request.getStartTime(), request.getEndTime(), request.getDurationInMinutes());
        return ResponseEntity.ok(new ApiResponse<>("Exam assigned successfully", null));
    }

    @PatchMapping("/{id}/visibility")
    @PreAuthorize("hasAuthority('ROLE_TEACHER')")
    public ResponseEntity<ApiResponse<Void>> toggleVisibility(
            @PathVariable Long id,
            @RequestParam boolean showResults) {
        
        examService.updateShowResultToStudent(id, showResults);
        return ResponseEntity.ok(new ApiResponse<>("Visibility updated successfully", null));
    }
}
