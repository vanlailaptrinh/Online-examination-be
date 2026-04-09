package com.meeting.springboot_meet.auth.presentation.controller;

import com.meeting.springboot_meet.auth.application.service.AuthService;

import com.meeting.springboot_meet.auth.presentation.payload.UserCreateRequest;
import com.meeting.springboot_meet.common.dto.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/users")
@RequiredArgsConstructor
public class AdminUserController {

    private final AuthService authService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> createUser(@Valid @RequestBody UserCreateRequest request) {
        authService.adminCreateUser(request);
        return ResponseEntity.ok(new ApiResponse<>("Admin created user successfully. Verification email sent.", null));
    }
}

