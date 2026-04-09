package com.meeting.springboot_meet.dashboard.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DashboardStats {
    // For Teachers
    private Long totalClassrooms;
    private Long totalExamsBuilt;
    private Long totalStudentsInClasses;

    // For Students
    private Long examsTaken;
    private Double averageScore;
}
