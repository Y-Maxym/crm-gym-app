package com.gym.crm.app.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gym.crm.app.rest.exception.ErrorCode;
import com.gym.crm.app.rest.exception.ErrorResponse;
import com.gym.crm.app.service.common.JwtService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static java.util.Objects.nonNull;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private static final String INVALID_ACCESS_TOKEN = "Invalid access token";
    private static final String ACCESS_TOKEN_HAS_EXPIRED = "Access token has expired";

    private final UserDetailsService userDetailsService;
    private final JwtService jwtService;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {

        final String authorization = request.getHeader("Authorization");
        try {
            if (nonNull(authorization) && authorization.startsWith("Bearer ")) {
                String token = authorization.substring(7);
                String username = jwtService.extractUsername(token);

                if (nonNull(username) && SecurityContextHolder.getContext().getAuthentication() == null) {

                    UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                    Authentication authentication = new UsernamePasswordAuthenticationToken(
                            userDetails,
                            userDetails.getPassword(),
                            userDetails.getAuthorities()
                    );

                    if (jwtService.isValid(token, username))
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
            filterChain.doFilter(request, response);
        } catch (SignatureException e) {
            writeUnauthorizedResponse(response, INVALID_ACCESS_TOKEN, ErrorCode.INVALID_ACCESS_TOKEN.getCode());
        } catch (ExpiredJwtException e) {
            writeUnauthorizedResponse(response, ACCESS_TOKEN_HAS_EXPIRED, ErrorCode.EXPIRED_ACCESS_TOKEN.getCode());
        }
    }

    private void writeUnauthorizedResponse(HttpServletResponse response, String message, Integer code) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        ErrorResponse error = new ErrorResponse(code, message);

        String json = objectMapper.writeValueAsString(error);

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        response.getWriter().write(json);
    }
}
