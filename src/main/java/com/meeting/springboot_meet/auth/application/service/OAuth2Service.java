package com.meeting.springboot_meet.auth.application.service;

import com.meeting.springboot_meet.auth.domain.model.ProviderType;
import com.meeting.springboot_meet.auth.domain.model.User;
import com.meeting.springboot_meet.auth.domain.model.UserProvider;
import com.meeting.springboot_meet.auth.domain.model.UserRole;
import com.meeting.springboot_meet.auth.domain.repository.UserProviderRepository;
import com.meeting.springboot_meet.auth.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OAuth2Service {

    private final UserRepository userRepository;
    private final UserProviderRepository userProviderRepository;

    @Transactional
    public User loginWithOAuth2(String email, String name, String providerId) {
        ProviderType providerType = ProviderType.valueOf(providerId.toUpperCase());

        return userRepository.findByEmail(email)
                .map(user -> attachProviderIfNeeded(user, providerType, providerId))
                .orElseGet(() -> createNewUser(email, name, providerType, providerId));
    }

    private User attachProviderIfNeeded(User user, ProviderType providerType, String providerId) {
        boolean exists = userProviderRepository
                .existsByUserIdAndProvider(user.getId(), providerType);

        if (!exists) {
            userProviderRepository.save(
                    UserProvider.builder()
                            .user(user)
                            .provider(providerType)
                            .providerUserId(providerId)
                            .build()
            );
        }
        return user;
    }

    private User createNewUser(String email, String name, ProviderType providerType, String providerId) {
        User user = User.builder()
                .email(email)
                .fullName(name)
                .roles(java.util.Set.of(UserRole.ROLE_STUDENT))
                .build();

        user = userRepository.save(user);

        userProviderRepository.save(
                UserProvider.builder()
                        .user(user)
                        .provider(providerType)
                        .providerUserId(providerId)
                        .build()
        );

        return user;
    }
}


