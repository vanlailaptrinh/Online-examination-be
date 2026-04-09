package com.meeting.springboot_meet.auth.domain.model;

import lombok.*;

import java.time.Instant;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    private Long id;
    private String email;
    private String fullName;
    @Builder.Default
    private boolean enabled = false;
    @Builder.Default
    private Instant createdAt = Instant.now();
    private List<RefreshToken> refreshTokens;
    private List<UserProvider> userProvider;
    private Set<UserRole> roles;
}