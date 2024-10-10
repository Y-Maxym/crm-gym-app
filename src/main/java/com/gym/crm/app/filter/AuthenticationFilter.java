package com.gym.crm.app.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gym.crm.app.error.ErrorMessage;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

import static java.util.Objects.isNull;

@Component
public class AuthenticationFilter extends OncePerRequestFilter {

    private static final List<String> EXCLUDED_URLS = List.of("/api/v1/login", "/api/v1/trainees/register", "/api/v1/trainers/register", "/swagger-ui/index.html", "/swagger-ui.html", "/v1/api-docs");

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws IOException, ServletException {
        String requestURI = request.getRequestURI();

        if (isAuthenticationRequired(requestURI)) {

            HttpSession session = request.getSession(false);
            if (isNull(session) || isNull(session.getAttribute("user"))) {

                writeUnauthorizedResponse(response);
                return;
            }
        }

        filterChain.doFilter(request, response);
    }

    private boolean isAuthenticationRequired(String uri) {
        return EXCLUDED_URLS.stream().noneMatch(uri::startsWith);
    }

    private void writeUnauthorizedResponse(HttpServletResponse response) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        ErrorMessage error = new ErrorMessage(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");

        String json = objectMapper.writeValueAsString(error);

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        response.getWriter().write(json);
    }
}
