package com.meeting.springboot_meet.evaluation.domain.model;

import lombok.*;

import java.time.Instant;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Attempt {
    private Long id;
    private Long examId;
    private String examTitle;
    private Long studentId;
    private Instant startTime;
    private Instant submitTime;
    private Double score;
    private List<Answer> answers;
}
