package com.meeting.springboot_meet.classroom.domain.model;

import lombok.*;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Invitation {
    private Long id;
    private String email;
    private Long classroomId;
    private String token;
    private boolean used;
    private Instant createdAt;
    private Instant expiresAt;
}
