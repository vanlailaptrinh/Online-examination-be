package com.meeting.springboot_meet.classroom.domain.model;

import lombok.*;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Classroom {
    private Long id;
    private String name;
    private String description;
    private Long teacherId;
    private Instant createdAt;
}
