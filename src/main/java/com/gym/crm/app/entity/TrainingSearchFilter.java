package com.gym.crm.app.entity;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class TrainingSearchFilter {

    private String username;
    private LocalDate from;
    private LocalDate to;
    private String profileName;
    private String trainingType;
}
