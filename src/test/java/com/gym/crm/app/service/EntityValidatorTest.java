package com.gym.crm.app.service;

import com.gym.crm.app.exception.EntityValidationException;
import com.gym.crm.app.logging.MessageHelper;
import com.gym.crm.app.service.common.EntityValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.gym.crm.app.util.Constants.ERROR_ENTITY_CANNOT_BE_NULL;
import static com.gym.crm.app.util.Constants.ERROR_ENTITY_ID_CANNOT_BE_NULL;
import static com.gym.crm.app.util.Constants.ERROR_ENTITY_ID_CANNOT_BE_ZERO;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class EntityValidatorTest {

    @Mock
    private MessageHelper messageHelper;

    @InjectMocks
    private EntityValidator entityValidator;

    @Test
    @DisplayName("Test check null id functionality")
    void givenNullId_whenCheckId_thenExceptionIsThrows() {
        // given
        given(messageHelper.getMessage(ERROR_ENTITY_ID_CANNOT_BE_NULL))
                .willReturn("Entity id cannot be null");

        // when && then
        assertThatThrownBy(() -> entityValidator.checkId(null))
                .isInstanceOf(EntityValidationException.class)
                .hasMessage("Entity id cannot be null");
    }

    @Test
    @DisplayName("Test check zero id functionality")
    void givenZeroId_whenCheckId_thenExceptionIsThrows() {
        // given
        long id = 0L;
        given(messageHelper.getMessage(ERROR_ENTITY_ID_CANNOT_BE_ZERO))
                .willReturn("Entity id cannot be less or equals zero");

        // when && then
        assertThatThrownBy(() -> entityValidator.checkId(id))
                .isInstanceOf(EntityValidationException.class)
                .hasMessage("Entity id cannot be less or equals zero");
    }

    @Test
    @DisplayName("Test check negative id functionality")
    void givenNegativeId_whenCheckId_thenExceptionIsThrows() {
        // given
        long id = -1L;
        given(messageHelper.getMessage(ERROR_ENTITY_ID_CANNOT_BE_ZERO))
                .willReturn("Entity id cannot be less or equals zero");

        // when && then
        assertThatThrownBy(() -> entityValidator.checkId(id))
                .isInstanceOf(EntityValidationException.class)
                .hasMessage("Entity id cannot be less or equals zero");
    }

    @Test
    @DisplayName("Test check valid id functionality")
    void givenValidId_whenCheckId_thenExceptionIsThrows() {
        // given
        long id = 1L;

        // when
        entityValidator.checkId(id);

        // then
        Assertions.assertDoesNotThrow(() -> entityValidator.checkId(id));
    }

    @Test
    @DisplayName("Test check null entity functionality")
    public void givenNullEntity_whenCheckEntity_thenExceptionIsThrows() {
        // given
        given(messageHelper.getMessage(ERROR_ENTITY_CANNOT_BE_NULL))
                .willReturn("Entity cannot be null");

        // when & then
        assertThatThrownBy(() -> entityValidator.checkEntity(null))
                .isInstanceOf(EntityValidationException.class)
                .hasMessage("Entity cannot be null");
    }

    @Test
    @DisplayName("Test check valid entity functionality")
    public void givenValidEntity_whenCheckEntity_thenSuccess() {
        // given
        Object entity = new Object();

        // when
        entityValidator.checkEntity(entity);

        // then
        Assertions.assertDoesNotThrow(() -> entityValidator.checkEntity(entity));
    }
}
