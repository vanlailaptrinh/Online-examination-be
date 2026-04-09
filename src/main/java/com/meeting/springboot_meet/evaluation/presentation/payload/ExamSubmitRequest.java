package com.meeting.springboot_meet.evaluation.presentation.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExamSubmitRequest {
    private Long examId;
    private List<AnswerRequest> answers;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class AnswerRequest {
        private Long questionId;
        private Set<Long> selectedOptionIds;
    }
}
