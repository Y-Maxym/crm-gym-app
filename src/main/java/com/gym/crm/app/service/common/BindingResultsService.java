package com.gym.crm.app.service.common;

import com.gym.crm.app.rest.exception.FieldErrorEntity;
import com.gym.crm.app.exception.ApplicationException;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.List;
import java.util.function.BiFunction;

@Service
public class BindingResultsService {

    public void handle(BindingResult bindingResult, BiFunction<String, List<FieldErrorEntity>, ? extends ApplicationException> exception, String message) {

        if (bindingResult.hasErrors()) {
            List<FieldErrorEntity> errors = bindingResult.getFieldErrors()
                    .stream()
                    .map(FieldErrorEntity::new)
                    .toList();

            throw exception.apply(message, errors);
        }
    }
}
