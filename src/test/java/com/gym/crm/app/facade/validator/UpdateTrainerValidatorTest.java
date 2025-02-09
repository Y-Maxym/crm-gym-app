package com.gym.crm.app.facade.validator;

import com.gym.crm.app.facade.validator.UpdateTrainerValidator;
import com.gym.crm.app.rest.model.TraineeCreateRequest;
import com.gym.crm.app.rest.model.UpdateTrainerProfileRequest;
import com.gym.crm.app.utils.EntityTestData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;

import static org.assertj.core.api.Assertions.assertThat;

class UpdateTrainerValidatorTest {

    private Errors errors;

    private UpdateTrainerValidator validator;

    @BeforeEach
    void setUp() {
        validator = new UpdateTrainerValidator();
    }

    @Test
    @DisplayName("Test valid request functionality")
    void givenValidRequest_whenValidate_thenNoErrors() {
        // given
        UpdateTrainerProfileRequest request = EntityTestData.getValidUpdateTrainerProfileRequest();
        errors = new BeanPropertyBindingResult(request, "updateTrainerProfileRequest");

        // when
        validator.validate(request, errors);

        // then
        assertThat(errors.getErrorCount()).isEqualTo(0);
    }

    @Test
    @DisplayName("Test null fields functionality")
    void givenNullRequest_whenValidate_thenHasErrors() {
        // given
        UpdateTrainerProfileRequest request = EntityTestData.getInvalidTrainerProfileRequest();
        errors = new BeanPropertyBindingResult(request, "updateTrainerProfileRequest");

        // when
        validator.validate(request, errors);

        // then
        assertThat(errors.getErrorCount()).isEqualTo(4);
        assertThat(errors.getFieldErrors()).extracting(ObjectError::getCode)
                .contains("last.name.empty.error", "first.name.empty.error", "specialization.empty.error", "isActive.empty.error");
    }

    @Test
    @DisplayName("Test supports Trainer request functionality")
    void whenSupports_thenReturnsTrue() {
        // when
        boolean actual = validator.supports(UpdateTrainerProfileRequest.class);

        // then
        assertThat(actual).isTrue();
    }

    @Test
    @DisplayName("Test supports Trainee request functionality")
    void whenSupports_thenReturnsFalse() {
        // when
        boolean actual = validator.supports(TraineeCreateRequest.class);

        // then
        assertThat(actual).isFalse();
    }

    @Test
    @DisplayName("Test fields longer than 100 chars functionality")
    void givenLongLastName_whenValidate_thenHasErrors() {
        // given
        UpdateTrainerProfileRequest request = EntityTestData.getValidUpdateTrainerProfileRequest();
        request.setFirstName("long".repeat(100));
        request.setLastName("long".repeat(100));
        request.setSpecialization("long".repeat(100));
        errors = new BeanPropertyBindingResult(request, "updateTrainerProfileRequest");

        // when
        validator.validate(request, errors);

        // then
        assertThat(errors.getErrorCount()).isEqualTo(3);
        assertThat(errors.getFieldErrors()).extracting(ObjectError::getCode)
                .containsExactly("first.name.length.error", "last.name.length.error", "specialization.length.error");
    }

    @Test
    @DisplayName("Test fields contain digit chars functionality")
    void givenFieldsContainDigits_whenValidate_thenHasErrors() {
        // given
        UpdateTrainerProfileRequest request = EntityTestData.getValidUpdateTrainerProfileRequest();
        request.setFirstName("123");
        request.setLastName("123");
        errors = new BeanPropertyBindingResult(request, "updateTrainerProfileRequest");

        // when
        validator.validate(request, errors);

        // then
        assertThat(errors.getErrorCount()).isEqualTo(2);
        assertThat(errors.getFieldErrors()).extracting(ObjectError::getCode)
                .containsExactly("first.name.digits.error", "last.name.digits.error");
    }
}