package com.meeting.springboot_meet.exam.domain.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Option {
    private Long id;
    private String content;

    @com.fasterxml.jackson.annotation.JsonProperty("isCorrect")
    private boolean isCorrect;
    
    private Long questionId;
}
