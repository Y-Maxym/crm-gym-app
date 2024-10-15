package com.gym.crm.app.service.common;

import com.gym.crm.app.entity.User;
import com.gym.crm.app.exception.AuthenticationException;
import com.gym.crm.app.exception.EntityValidationException;
import com.gym.crm.app.rest.exception.ErrorCode;
import com.gym.crm.app.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    private static final String INVALID_USERNAME_OR_PASSWORD = "Invalid username or password";

    @Mock
    private UserService userService;

    @Mock
    private UserProfileService profileService;

    @InjectMocks
    private AuthService authService;

    @Test
    @DisplayName("Test authenticate with correct credentials functionality")
    void givenValidCredentials_whenAuthenticate_thenReturnUser() {
        // given
        String username = "username";
        String password = "password";
        String storedPassword = "storedPassword";

        User user = User.builder().username(username).password(storedPassword).build();

        given(userService.findByUsername(username))
                .willReturn(user);
        given(profileService.isPasswordCorrect(password, storedPassword))
                .willReturn(true);

        // when
        User result = authService.authenticate(username, password);

        // then
        assertEquals(user, result);
    }

    @Test
    @DisplayName("Test authenticate with invalid username functionality")
    void givenInvalidUsername_whenAuthenticate_thenThrowAuthenticationException() {
        // given
        String username = "username";
        String password = "password";

        String exceptionMessage = "User with username %s not found".formatted(username);

        given(userService.findByUsername(username))
                .willThrow(new EntityValidationException(exceptionMessage, ErrorCode.INVALID_USERNAME_OR_PASSWORD.getCode()));

        // when
        AuthenticationException exception = assertThrows(AuthenticationException.class,
                () -> authService.authenticate(username, password));

        // then
        assertThat(INVALID_USERNAME_OR_PASSWORD).isEqualTo(exception.getMessage());
        assertThat(exception).hasRootCauseMessage(exceptionMessage);
    }

    @Test
    @DisplayName("Test authenticate with invalid passwpord functionality")
    void givenInvalidPassword_whenAuthenticate_thenThrowAuthenticationException() {
        // given
        String username = "username";
        String password = "password";
        String storedPassword = "storedPassword";

        User user = User.builder().username(username).password(storedPassword).build();

        given(userService.findByUsername(username))
                .willReturn(user);
        given(profileService.isPasswordCorrect(password, storedPassword))
                .willReturn(false);

        // when
        AuthenticationException exception = assertThrows(AuthenticationException.class,
                () -> authService.authenticate(username, password));

        // then
        assertThat(INVALID_USERNAME_OR_PASSWORD).isEqualTo(exception.getMessage());
    }
}