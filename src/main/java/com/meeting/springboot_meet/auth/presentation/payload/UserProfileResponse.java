package com.meeting.springboot_meet.auth.presentation.payload;

import com.meeting.springboot_meet.auth.domain.model.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserProfileResponse {
    private Long id;
    private String email;
    private String fullName;
    private Set<UserRole> roles;
}
