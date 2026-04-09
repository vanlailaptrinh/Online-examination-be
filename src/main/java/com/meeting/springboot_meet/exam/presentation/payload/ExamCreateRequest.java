package com.meeting.springboot_meet.exam.presentation.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExamCreateRequest {
    private String title;
    private String description;
    private boolean showResultToStudent;
    private List<QuestionRequest> questions;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class QuestionRequest {
        private String content;
        private String type; // SINGLE_CHOICE, MULTIPLE_CHOICE
        private List<OptionRequest> options;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class OptionRequest {
        private String content;

        @com.fasterxml.jackson.annotation.JsonProperty("isCorrect")
        private boolean isCorrect;
    }
}
