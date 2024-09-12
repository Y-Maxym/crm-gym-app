package com.gym.crm.app.entity;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Builder(toBuilder = true)
@ToString
@EqualsAndHashCode
public final class Trainee {

    private final Long id;
    private final Long userId;
    private final LocalDate dateOfBirth;
    private final String address;
}
