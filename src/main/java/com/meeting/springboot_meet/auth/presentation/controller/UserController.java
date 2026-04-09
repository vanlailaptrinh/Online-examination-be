package com.meeting.springboot_meet.auth.presentation.controller;

import com.meeting.springboot_meet.auth.domain.model.User;
import com.meeting.springboot_meet.auth.domain.repository.UserRepository;
import com.meeting.springboot_meet.auth.presentation.payload.UserProfileResponse;
import com.meeting.springboot_meet.common.dto.response.ApiResponse;
import com.meeting.springboot_meet.security.service.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;

    @GetMapping("/profile")
    public ResponseEntity<ApiResponse<UserProfileResponse>> getProfile(
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        
        User user = userRepository.findById(userDetails.getId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        UserProfileResponse response = UserProfileResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .fullName(user.getFullName())
                .roles(user.getRoles())
                .build();

        return ResponseEntity.ok(new ApiResponse<>("Fetched profile successfully", response));
    }
}
