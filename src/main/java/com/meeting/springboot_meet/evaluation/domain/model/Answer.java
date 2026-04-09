package com.meeting.springboot_meet.evaluation.domain.model;

import lombok.*;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Answer {
    private Long id;
    private Long attemptId;
    private Long questionId;
    private Set<Long> selectedOptionIds;
}
