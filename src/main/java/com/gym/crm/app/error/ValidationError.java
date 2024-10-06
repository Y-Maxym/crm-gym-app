package com.gym.crm.app.error;

import java.util.List;

public record ValidationError
        (
                int code,
                String message,
                List<FieldErrorEntity> fields
        ) {

}
