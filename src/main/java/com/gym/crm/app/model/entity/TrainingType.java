package com.gym.crm.app.model.entity;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder(toBuilder = true)
@ToString
@EqualsAndHashCode
public final class TrainingType {

    private final Long id;
    private final String trainingTypeName;
}
