package com.meeting.springboot_meet.exam.domain.model;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Question {
    private Long id;
    private String content;
    private String type; // SINGLE_CHOICE, MULTIPLE_CHOICE
    private Long examId;
    private List<Option> options;
}
