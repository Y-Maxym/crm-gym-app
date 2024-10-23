package com.gym.crm.app.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gym.crm.app.rest.exception.ErrorResponse;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.core.annotation.Order;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

import static com.gym.crm.app.rest.exception.ErrorCode.UNAUTHORIZED_ERROR;
import static java.util.Objects.isNull;

@Component
@Order(4)
public class AuthenticationFilter extends OncePerRequestFilter {

    private static final String UNAUTHORIZED_MESSAGE = "Unauthorized";
    private static final List<String> EXCLUDED_URLS = List.of("/api/v1/login", "/api/v1/trainees/register", "/api/v1/trainers/register",
            "/swagger-ui", "/v1/api-docs", "/actuator/prometheus");

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws IOException, ServletException {
        if (hasNoValidSession(request)) {
            writeUnauthorizedResponse(response);
            return;
        }

        filterChain.doFilter(request, response);
    }

    private boolean hasNoValidSession(HttpServletRequest request) {
        String uri = request.getRequestURI();
        boolean isAuthenticationRequired = EXCLUDED_URLS.stream()
                .noneMatch(uri::startsWith);

        if (isAuthenticationRequired) {
            HttpSession session = request.getSession(false);

            return isNull(session) || isNull(session.getAttribute("user"));
        }

        return false;
    }

    private void writeUnauthorizedResponse(HttpServletResponse response) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        ErrorResponse error = new ErrorResponse(UNAUTHORIZED_ERROR.getCode(), UNAUTHORIZED_MESSAGE);

        String json = objectMapper.writeValueAsString(error);

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        response.getWriter().write(json);
    }
}
