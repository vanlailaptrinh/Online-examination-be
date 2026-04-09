package com.meeting.springboot_meet.security.service;

import com.meeting.springboot_meet.auth.domain.model.User;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.Instant;
import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

@Data
public class UserDetailsImpl implements UserDetails {

    private final Long id;
    private final String email;
    private final String fullName;
    private final boolean enabled;
    private final Instant createdAt;
    private final Collection<? extends GrantedAuthority> authorities;
    private final User user; // Reference to domain user

    public UserDetailsImpl(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.fullName = user.getFullName();
        this.enabled = user.isEnabled();
        this.createdAt = user.getCreatedAt();
        this.user = user;
        this.authorities = user.getRoles() != null 
            ? user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.name()))
                .collect(Collectors.toList())
            : Collections.emptyList();
    }


    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public String getPassword() {
        return "";
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }


    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
