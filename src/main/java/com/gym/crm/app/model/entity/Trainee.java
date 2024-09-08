package com.gym.crm.app.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Trainee {

    private Long id;
    private Long userId;
    private LocalDate dateOfBirth;
    private String address;
}
