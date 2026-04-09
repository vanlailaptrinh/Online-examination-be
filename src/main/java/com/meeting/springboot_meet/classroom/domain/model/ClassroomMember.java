package com.meeting.springboot_meet.classroom.domain.model;

import lombok.*;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClassroomMember {
    private Long id;
    private Long classroomId;
    private Long userId;
    private String userName;
    private String userEmail;
    private String status;
    private Instant joinedAt;
}
