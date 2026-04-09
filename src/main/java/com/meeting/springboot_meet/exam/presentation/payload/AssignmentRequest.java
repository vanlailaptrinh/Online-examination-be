package com.meeting.springboot_meet.exam.presentation.payload;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AssignmentRequest {
    @NotEmpty(message = "Danh sách lớp học không được để trống")
    private List<Long> classroomIds;

    @NotNull(message = "Thời gian bắt đầu không được để trống")
    private Instant startTime;

    @NotNull(message = "Thời gian kết thúc không được để trống")
    private Instant endTime;

    @NotNull(message = "Thời gian làm bài không được để trống")
    private Integer durationInMinutes;
}
