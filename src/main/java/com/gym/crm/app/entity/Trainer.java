package com.gym.crm.app.entity;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder(toBuilder = true)
@ToString
@EqualsAndHashCode
public final class Trainer {

    private final Long id;
    private final Long userId;
    private final Long specializationId;
}
