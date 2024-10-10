package com.gym.crm.app.validator;

import com.gym.crm.app.rest.model.TraineeCreateRequest;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import static java.util.Objects.isNull;

@Component
public class CreateTraineeValidator implements Validator {

    @Override
    public boolean supports(@NonNull Class<?> clazz) {
        return TraineeCreateRequest.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(@NonNull Object target, @NonNull Errors errors) {
        TraineeCreateRequest request = (TraineeCreateRequest) target;

        String firstName = request.getFirstName();
        String lastName = request.getLastName();

        if (isNull(firstName) || firstName.isBlank()) {
            errors.rejectValue("firstName", "first.name.empty.error", "First name is required");
        }
        if (isNull(lastName) || lastName.isBlank()) {
            errors.rejectValue("lastName", "last.name.empty.error", "Last name is required");
        }
    }
}