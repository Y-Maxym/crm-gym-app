package com.gym.crm.app.service;

import com.gym.crm.app.exception.EntityValidationException;
import com.gym.crm.app.logging.MessageHelper;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.gym.crm.app.util.Constants.ERROR_ENTITY_CANNOT_BE_NULL;
import static com.gym.crm.app.util.Constants.ERROR_ENTITY_ID_CANNOT_BE_NULL;
import static com.gym.crm.app.util.Constants.ERROR_ENTITY_ID_CANNOT_BE_ZERO;
import static java.util.Objects.isNull;

@Component
@Setter(onMethod_ = @Autowired)
public class EntityExceptionHelper {

    private MessageHelper messageHelper;

    public void checkId(Long id) {
        if (isNull(id)) {
            throw new EntityValidationException(messageHelper.getMessage(ERROR_ENTITY_ID_CANNOT_BE_NULL));
        }

        if (id <= 0) {
            throw new EntityValidationException(messageHelper.getMessage(ERROR_ENTITY_ID_CANNOT_BE_ZERO));
        }
    }

    public void checkEntity(Object entity) {
        if (isNull(entity)) {
            throw new EntityValidationException(messageHelper.getMessage(ERROR_ENTITY_CANNOT_BE_NULL));
        }
    }
}
