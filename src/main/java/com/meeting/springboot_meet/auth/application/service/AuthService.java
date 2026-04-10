package com.meeting.springboot_meet.auth.application.service;

import com.meeting.springboot_meet.auth.domain.exception.AuthException;
import com.meeting.springboot_meet.auth.domain.exception.ErrorCode;
import com.meeting.springboot_meet.auth.domain.model.User;
import com.meeting.springboot_meet.auth.domain.model.UserRole;
import com.meeting.springboot_meet.auth.domain.repository.UserRepository;
import com.meeting.springboot_meet.auth.presentation.payload.LoginResult;
import com.meeting.springboot_meet.auth.presentation.payload.LoginRequest;
import com.meeting.springboot_meet.auth.presentation.payload.RegisterRequest;
import com.meeting.springboot_meet.auth.presentation.payload.UserCreateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Collections;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final RefreshTokenService refreshTokenService;
    private final UserProviderService userProviderService;
    private final AuthenticationService authenticationService;
    private final TokenService tokenService;
    private final PasswordEncoder passwordEncoder;
    private final RedisVerificationService redisVerificationService;
    private final EmailService emailService;

    @Transactional
    public void register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new AuthException(ErrorCode.EMAIL_ALREADY_EXISTS);
        }

        String hashedPassword = passwordEncoder.encode(request.getPassword());

        String token = UUID.randomUUID().toString();

        // PUBLIC REGISTER: Người dùng chọn STUDENT hoặc TEACHER, mặc định STUDENT
        UserRole selectedRole = (request.getRole() != null && !request.getRole().isBlank())
                ? UserRole.valueOf("ROLE_" + request.getRole())
                : UserRole.ROLE_STUDENT;
        Set<UserRole> roles = Collections.singleton(selectedRole);

        redisVerificationService.saveVerification(token,
                request.getEmail(),
                hashedPassword,
                roles);

        emailService.sendVerificationEmail(request.getEmail(), token);
    }

    @Transactional
    public void adminCreateUser(UserCreateRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new AuthException(ErrorCode.EMAIL_ALREADY_EXISTS);
        }

        String hashedPassword = passwordEncoder.encode(request.getPassword());

        String token = UUID.randomUUID().toString();

        // ADMIN CREATE: Use roles from request, default to STUDENT if empty
        Set<UserRole> roles = (request.getRoles() == null || request.getRoles().isEmpty()) 
            ? Collections.singleton(UserRole.ROLE_STUDENT) 
            : request.getRoles();

        redisVerificationService.saveVerification(token,
                request.getEmail(),
                hashedPassword,
                roles);

        emailService.sendVerificationEmail(request.getEmail(), token);
    }

    @Transactional
    public void verifyEmailToken(String token) {
        String value = redisVerificationService.getVerification(token);
        if (value == null) {
            throw new RuntimeException("Token không hợp lệ hoặc hết hạn");
        }

        String[] parts = value.split(":", 3);
        String email = parts[0];
        String hashedPassword = parts[1];
        Set<UserRole> roles = (parts.length > 2 && !parts[2].isEmpty())
            ? Arrays.stream(parts[2].split(","))
                    .map(UserRole::valueOf)
                    .collect(Collectors.toSet())
            : Collections.singleton(UserRole.ROLE_STUDENT);

        if (userRepository.existsByEmail(email)) {
            throw new RuntimeException("Tài khoản đã tồn tại hoặc đã được kích hoạt");
        }

        User user = userRepository.save(
                User.builder()
                        .email(email)
                        .enabled(true)
                        .roles(roles)
                        .build()
        );

        userProviderService.createLocalProvider(user, email, hashedPassword);
        
        redisVerificationService.deleteVerification(token);
    }

    public LoginResult login(LoginRequest request, String userAgent, String ip) {
        User user = authenticationService.authenticateLocal(
                request.getEmail(),
                request.getPassword()
        );
        return tokenService.generateTokens(user, userAgent, ip);
    }

    public LoginResult refreshAccessToken(String oldToken, String userAgent, String ip) {
        return tokenService.refreshAccessToken(oldToken, userAgent, ip);
    }

    @Transactional
    public void logout(String refreshToken) {
        refreshTokenService.revokeByToken(refreshToken);
    }
}


