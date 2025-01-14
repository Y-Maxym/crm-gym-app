package com.gym.crm.app.facade.validator;

import com.gym.crm.app.entity.RefreshToken;
import com.gym.crm.app.exception.RefreshTokenException;
import com.gym.crm.app.rest.exception.ErrorCode;
import com.gym.crm.app.security.RefreshTokenService;
import com.gym.crm.app.utils.EntityTestData;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class RefreshTokenValidatorTest {

    @Mock
    private RefreshTokenService refreshTokenService;

    @InjectMocks
    private RefreshTokenValidator refreshTokenValidator;

    @Test
    @DisplayName("Test validate by valid token functionality")
    void givenValidToken_whenValidate_thenDoesNotThrowException() {
        // given
        RefreshToken refreshToken = EntityTestData.getTransientValidRefreshToken();
        String token = refreshToken.getToken();

        given(refreshTokenService.findByToken(token))
                .willReturn(refreshToken);

        // when & then
        assertDoesNotThrow(() -> refreshTokenValidator.validate(token));
    }

    @Test
    @DisplayName("Test validate by null token functionality")
    void givenNullToken_whenValidate_thenThrowException() {
        // when & then
        RefreshTokenException ex = assertThrows(RefreshTokenException.class, () -> {
            refreshTokenValidator.validate(null);
        });

        assertThat(ex.getMessage()).isEqualTo("Invalid Refresh Token");
        assertThat(ex.getCode()).isEqualTo(ErrorCode.INVALID_REFRESH_TOKEN.getCode());
    }

    @Test
    @DisplayName("Test validate by invalid token functionality")
    void givenInvalidToken_whenValidate_thenThrowException() {
        // given
        String token = "invalid_token";

        given(refreshTokenService.findByToken(token))
                .willReturn(null);

        // when & then
        RefreshTokenException ex = assertThrows(RefreshTokenException.class, () -> {
            refreshTokenValidator.validate(token);
        });

        assertThat(ex.getMessage()).isEqualTo("Invalid Refresh Token");
        assertThat(ex.getCode()).isEqualTo(ErrorCode.INVALID_REFRESH_TOKEN.getCode());
    }

    @Test
    @DisplayName("Test validate by expired token functionality")
    void givenExpiredToken_whenValidate_thenThrowException() {
        // given
        String token = "expired_token";
        RefreshToken expiredRefreshToken = EntityTestData.getExpiredRefreshToken();

        given(refreshTokenService.findByToken(token))
                .willReturn(expiredRefreshToken);

        // when & then
        RefreshTokenException ex = assertThrows(RefreshTokenException.class, () -> {
            refreshTokenValidator.validate(token);
        });

        assertThat(ex.getMessage()).isEqualTo("Invalid Refresh Token");
        assertThat(ex.getCode()).isEqualTo(ErrorCode.INVALID_REFRESH_TOKEN.getCode());
    }
}