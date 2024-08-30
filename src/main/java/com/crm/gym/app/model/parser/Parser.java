package com.crm.gym.app.model.parser;

public interface Parser<I, O> {

    O parse(I input);
}
