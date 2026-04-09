package com.meeting.springboot_meet.classroom.presentation.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClassroomCreateRequest {
    private String name;
    private String description;
}
