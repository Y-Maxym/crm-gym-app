package com.gym.crm.app.validator;

import com.gym.crm.app.rest.model.UpdateTrainerProfileRequest;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import static java.util.Objects.isNull;

@Component
public class UpdateTrainerValidator implements Validator {

    @Override
    public boolean supports(@NonNull Class<?> clazz) {
        return UpdateTrainerProfileRequest.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(@NonNull Object target, @NonNull Errors errors) {
        UpdateTrainerProfileRequest request = (UpdateTrainerProfileRequest) target;

        String firstName = request.getFirstName();
        String lastName = request.getLastName();
        String specialization = request.getSpecialization();
        Boolean isActive = request.getIsActive();

        if (isNull(firstName) || firstName.isBlank()) {
            errors.rejectValue("firstName", "first.name.empty.error", "First name is required");
        }
        if (isNull(lastName) || lastName.isBlank()) {
            errors.rejectValue("lastName", "last.name.empty.error", "Last name is required");
        }
        if (isNull(specialization) || specialization.isBlank()) {
            errors.rejectValue("specialization", "specialization.empty.error", "Specialization is required");
        }
        if (isNull(isActive)) {
            errors.rejectValue("isActive", "isActive.empty.error", "Active is required");
        }
    }
}
