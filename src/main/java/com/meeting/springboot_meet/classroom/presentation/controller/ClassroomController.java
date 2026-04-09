package com.meeting.springboot_meet.classroom.presentation.controller;

import com.meeting.springboot_meet.classroom.application.service.ClassroomService;
import com.meeting.springboot_meet.classroom.domain.model.Classroom;
import com.meeting.springboot_meet.classroom.domain.model.ClassroomMember;
import com.meeting.springboot_meet.classroom.domain.model.Invitation;
import com.meeting.springboot_meet.classroom.presentation.payload.ClassroomCreateRequest;
import com.meeting.springboot_meet.classroom.presentation.payload.InviteRequest;
import com.meeting.springboot_meet.common.dto.response.ApiResponse;
import com.meeting.springboot_meet.security.service.UserDetailsImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/classrooms")
@RequiredArgsConstructor
public class ClassroomController {

    private final ClassroomService classroomService;

    @PostMapping
    @PreAuthorize("hasRole('TEACHER')")
    public ResponseEntity<ApiResponse<Classroom>> createClassroom(
            @Valid @RequestBody ClassroomCreateRequest request,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        
        Classroom classroom = classroomService.createClassroom(
                request.getName(), 
                request.getDescription(), 
                userDetails.getId()
        );
        return ResponseEntity.ok(new ApiResponse<>("Classroom created successfully", classroom));
    }

    @GetMapping("/teacher")
    @PreAuthorize("hasRole('TEACHER')")
    public ResponseEntity<ApiResponse<List<Classroom>>> getTeacherClassrooms(
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        
        List<Classroom> classrooms = classroomService.getTeacherClassrooms(userDetails.getId());
        return ResponseEntity.ok(new ApiResponse<>("Fetched classrooms successfully", classrooms));
    }

    @PostMapping("/{id}/invite")
    @PreAuthorize("hasRole('TEACHER')")
    public ResponseEntity<ApiResponse<Invitation>> inviteStudent(
            @PathVariable Long id,
            @Valid @RequestBody InviteRequest request) {
        
        Invitation invitation = classroomService.inviteStudent(id, request.getEmail());
        return ResponseEntity.ok(new ApiResponse<>("Invitation sent successfully", invitation));
    }

    @PostMapping("/join")
    public ResponseEntity<ApiResponse<Void>> joinClassroom(@RequestParam String token) {
        
        classroomService.joinClassroom(token);
        return ResponseEntity.ok(new ApiResponse<>("Joined classroom successfully", null));
    }

    @GetMapping("/{id}/members")
    @PreAuthorize("hasRole('TEACHER')")
    public ResponseEntity<ApiResponse<List<ClassroomMember>>> getClassroomMembers(
            @PathVariable Long id) {
        
        List<ClassroomMember> members = classroomService.getClassroomMembers(id);
        return ResponseEntity.ok(new ApiResponse<>("Fetched classroom members successfully", members));
    }
}
