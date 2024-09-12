package com.gym.crm.app.entity;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.time.Duration;
import java.time.LocalDateTime;

@Getter
@Builder(toBuilder = true)
@ToString
@EqualsAndHashCode
public final class Training {

    private final Long id;
    private final Long traineeId;
    private final Long trainerId;
    private final String trainingName;
    private final Long trainingTypeId;
    private final LocalDateTime trainingDate;
    private final Duration trainingDuration;
}
