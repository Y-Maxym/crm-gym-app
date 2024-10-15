package com.gym.crm.app.service.common;

import com.gym.crm.app.exception.EntityValidationException;
import com.gym.crm.app.logging.MessageHelper;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.gym.crm.app.rest.exception.ErrorCode.NULL_ENTITY;
import static com.gym.crm.app.rest.exception.ErrorCode.NULL_ENTITY_ID;
import static com.gym.crm.app.rest.exception.ErrorCode.ZERO_ENTITY_ID;
import static com.gym.crm.app.util.Constants.ERROR_ENTITY_CANNOT_BE_NULL;
import static com.gym.crm.app.util.Constants.ERROR_ENTITY_ID_CANNOT_BE_NULL;
import static com.gym.crm.app.util.Constants.ERROR_ENTITY_ID_CANNOT_BE_ZERO;
import static java.util.Objects.isNull;



@Component
@Setter(onMethod_ = @Autowired)
public class EntityValidator {

    private MessageHelper messageHelper;

    public void checkId(Long id) {
        if (isNull(id)) {
            throw new EntityValidationException(messageHelper.getMessage(ERROR_ENTITY_ID_CANNOT_BE_NULL), NULL_ENTITY_ID.getCode());
        }

        if (id <= 0) {
            throw new EntityValidationException(messageHelper.getMessage(ERROR_ENTITY_ID_CANNOT_BE_ZERO), ZERO_ENTITY_ID.getCode());
        }
    }

    public void checkEntity(Object entity) {
        if (isNull(entity)) {
            throw new EntityValidationException(messageHelper.getMessage(ERROR_ENTITY_CANNOT_BE_NULL), NULL_ENTITY.getCode());
        }
    }
}
