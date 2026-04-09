package com.meeting.springboot_meet.exam.domain.model;

import lombok.*;

import java.time.Instant;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Exam {
    private Long id;
    private String title;
    private String description;
    private Long teacherId;
    private boolean showResultToStudent = true;
    private Instant createdAt;
    private List<Question> questions;
    private Integer durationInMinutes;
    private List<Long> classroomIds;
}
