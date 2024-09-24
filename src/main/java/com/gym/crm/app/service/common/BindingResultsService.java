package com.gym.crm.app.service.common;

import com.gym.crm.app.error.FieldErrorEntity;
import com.gym.crm.app.exception.ApplicationException;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.List;
import java.util.function.Function;

@Service
public class BindingResultsService {

    public void handle(BindingResult bindingResult, Function<List<FieldErrorEntity>, ? extends ApplicationException> exception) {

        if (bindingResult.hasErrors()) {
            List<FieldErrorEntity> errors = bindingResult.getFieldErrors()
                    .stream()
                    .map(FieldErrorEntity::new)
                    .toList();

            throw exception.apply(errors);
        }
    }
}
