package com.gym.crm.app.repository;

import com.gym.crm.app.entity.TrainingType;

import java.util.Optional;

public interface TrainingTypeRepository {

    Optional<TrainingType> findByName(String typeName);
}
