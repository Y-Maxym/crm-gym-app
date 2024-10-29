package com.gym.crm.app.service.common;

import com.gym.crm.app.security.JwtService;
import io.jsonwebtoken.ExpiredJwtException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThrows;

@ExtendWith(MockitoExtension.class)
class JwtServiceTest {
    private final Duration duration = Duration.ofHours(1);

    @InjectMocks
    private JwtService jwtService;

    @Test
    @DisplayName("Test generate token functionality")
    void givenUsername_whenGenerateToken_thenValidTokenGenerated() {
        // given
        String username = "test";
        ReflectionTestUtils.setField(jwtService, "duration", duration);

        // when
        String actual = jwtService.generateToken(username);
        String extractedUsername = jwtService.extractUsername(actual);

        // then
        assertThat(actual).isNotNull();
        assertThat(extractedUsername).isEqualTo(username);
        assertThat(jwtService.isValid(actual, username)).isTrue();
    }

    @Test
    @DisplayName("Test extract username functionality")
    void givenUsername_extractUsername_thenCorrectUsernameShouldBeReturned() {
        // given
        String username = "test";
        ReflectionTestUtils.setField(jwtService, "duration", duration);
        String actual = jwtService.generateToken(username);

        // when
        String extractedUsername = jwtService.extractUsername(actual);

        // then
        assertThat(extractedUsername).isEqualTo(username);
    }

    @Test
    @DisplayName("Test expired token functionality")
    void givenExpiredToken_whenIsValid_thenTokenIsExpired() {
        // given
        String username = "test";
        ReflectionTestUtils.setField(jwtService, "duration", Duration.ofMillis(1));

        // when & then
        assertThrows(ExpiredJwtException.class, () -> {
            String actual = jwtService.generateToken(username);
            Thread.sleep(100);
            jwtService.isValid(actual, username);
        });
    }

    @Test
    @DisplayName("Test expired token functionality")
    void givenToken_whenIsValid_thenTokenIsInvalid() {
        // given
        String username = "test";
        ReflectionTestUtils.setField(jwtService, "duration", duration);

        // when
        String actual = jwtService.generateToken(username);
        Boolean isValid = jwtService.isValid(actual, "another username");

        // then
        assertThat(isValid).isFalse();
    }
}