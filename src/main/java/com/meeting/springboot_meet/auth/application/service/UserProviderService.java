package com.meeting.springboot_meet.auth.application.service;

import com.meeting.springboot_meet.auth.domain.model.ProviderType;
import com.meeting.springboot_meet.auth.domain.model.UserProvider;
import com.meeting.springboot_meet.auth.domain.repository.UserProviderRepository;
import com.meeting.springboot_meet.auth.domain.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserProviderService {

    private final UserProviderRepository userProviderRepository;

    public void createLocalProvider(User user, String email, String hashedPassword) {
        UserProvider provider = UserProvider.builder()
                .provider(ProviderType.LOCAL)
                .providerUserId(email)
                .passwordHash(hashedPassword)
                .user(user)
                .build();

        userProviderRepository.save(provider);
    }
}


