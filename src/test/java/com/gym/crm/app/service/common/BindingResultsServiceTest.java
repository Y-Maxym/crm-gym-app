package com.gym.crm.app.service.common;

import com.gym.crm.app.error.FieldErrorEntity;
import com.gym.crm.app.exception.ApplicationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.List;
import java.util.function.BiFunction;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class BindingResultsServiceTest {

    @Mock
    private BiFunction<String, List<FieldErrorEntity>, ApplicationException> exceptionFunction;

    @Mock
    private BindingResult bindingResult;

    @InjectMocks
    private BindingResultsService bindingResultsService;

    @Test
    @DisplayName("Test handle with errors functionality")
    void givenBindingResultHasErrors_whenHandle_thenExceptionIsThrown() {
        // given
        String errorMessage = "error message";
        FieldError fieldError = new FieldError("objectName", "field", "defaultMessage");
        ApplicationException exception = mock(ApplicationException.class);

        given(bindingResult.hasErrors())
                .willReturn(true);
        given(bindingResult.getFieldErrors())
                .willReturn(List.of(fieldError));
        given(exceptionFunction.apply(any(), any()))
                .willReturn(exception);

        // when
        assertThrows(ApplicationException.class, () ->
                bindingResultsService.handle(bindingResult, exceptionFunction, errorMessage));

        // then
        verify(exceptionFunction).apply(eq(errorMessage), any());
    }

    @Test
    @DisplayName("Test handle without errors functionality")
    void givenBindingResultHasNoErrors_whenHandle_thenNoExceptionIsThrown() {
        // given
        String errorMessage = "error message";
        given(bindingResult.hasErrors())
                .willReturn(false);

        // when
        bindingResultsService.handle(bindingResult, exceptionFunction, errorMessage);

        // then
        verify(exceptionFunction, never()).apply(eq(errorMessage), any());
    }
}