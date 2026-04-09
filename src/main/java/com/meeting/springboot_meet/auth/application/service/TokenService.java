package com.meeting.springboot_meet.auth.application.service;

import com.meeting.springboot_meet.auth.domain.model.RefreshToken;
import com.meeting.springboot_meet.auth.domain.model.User;
import com.meeting.springboot_meet.auth.presentation.payload.LoginResult;
import com.meeting.springboot_meet.auth.presentation.payload.AuthResponse;
import com.meeting.springboot_meet.auth.domain.exception.AuthException;
import com.meeting.springboot_meet.auth.domain.exception.ErrorCode;
import com.meeting.springboot_meet.security.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class TokenService {

    private final JwtUtil jwtUtil;
    private final RefreshTokenService refreshTokenService;

    private String generateAccessToken(User user) {
        return jwtUtil.generateAccessToken(user.getId(), user.getRoles());
    }

    private String generateRefreshToken() {
        return jwtUtil.generateRefreshToken();
    }

    public LoginResult generateTokens(User user, String userAgent, String ip) {

        String accessToken = jwtUtil.generateAccessToken(user.getId(), user.getRoles());
        String refreshToken = jwtUtil.generateRefreshToken();


        refreshTokenService.createRefreshToken(user, refreshToken, userAgent, ip);

        return new LoginResult(
                new AuthResponse(accessToken),
                refreshToken
        );
    }

    @Transactional
    public LoginResult refreshAccessToken(String oldToken, String userAgent, String ip) {

        RefreshToken refreshToken = refreshTokenService.verifyToken(oldToken);
        if (Boolean.TRUE.equals(refreshToken.getRevoked())) {
            refreshTokenService.revokeAllByUser(refreshToken.getUser());

            throw new AuthException(
                    ErrorCode.INVALID_REFRESH_TOKEN
            );
        }

        User user = refreshToken.getUser();

        // reuse create new token
        String newAccessToken = generateAccessToken(user);
        String newRefreshToken = generateRefreshToken();

        // revoke old token
        refreshToken.setRevoked(true);
        refreshToken.setRevokedAt(Instant.now());
        refreshToken.setReplacedByToken(newRefreshToken);

        refreshTokenService.createRefreshToken(user, newRefreshToken, userAgent, ip);

        return new LoginResult(
                new AuthResponse(newAccessToken),
                newRefreshToken
        );
    }
}


