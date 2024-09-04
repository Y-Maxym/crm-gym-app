package com.crm.gym.app.model.parser;

import lombok.NonNull;

public interface Parser<I, O> {

    O parse(@NonNull I input);
}
