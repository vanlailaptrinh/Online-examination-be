package com.meeting.springboot_meet.security.oauth2;

import com.meeting.springboot_meet.auth.application.service.TokenService;
import com.meeting.springboot_meet.auth.presentation.payload.LoginResult;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final TokenService tokenService;

    @Value("${frontend.callback-url}")
    private String frontendCallbackUrl;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        
        OAuth2UserPrincipal principal = (OAuth2UserPrincipal) authentication.getPrincipal();
        
        // Generate tokens
        LoginResult loginResult = tokenService.generateTokens(
                principal.getUserDetails().getUser(), 
                request.getHeader("User-Agent"), 
                request.getRemoteAddr()
        );

        String targetUrl = UriComponentsBuilder.fromUriString(frontendCallbackUrl)
                .queryParam("token", loginResult.getAuthResponse().getAccessToken())
                .queryParam("refreshToken", loginResult.getRefreshToken())
                .build()
                .toUriString();

        if (response.isCommitted()) {
            logger.debug("Response has already been committed. Unable to redirect to " + targetUrl);
            return;
        }

        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }
}
