package com.meeting.springboot_meet.dashboard.presentation.controller;

import com.meeting.springboot_meet.common.dto.response.ApiResponse;
import com.meeting.springboot_meet.dashboard.application.dto.DashboardStats;
import com.meeting.springboot_meet.dashboard.application.service.DashboardService;
import com.meeting.springboot_meet.security.service.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardService dashboardService;

    @GetMapping("/stats")
    public ResponseEntity<ApiResponse<DashboardStats>> getStats(
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        
        boolean isTeacher = userDetails.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_TEACHER"));

        DashboardStats stats = dashboardService.getStats(userDetails.getId(), isTeacher);
        return ResponseEntity.ok(new ApiResponse<>("Fetched dashboard stats", stats));
    }
}
