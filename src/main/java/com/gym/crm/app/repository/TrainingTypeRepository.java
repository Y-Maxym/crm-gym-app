package com.gym.crm.app.repository;

import com.gym.crm.app.entity.TrainingType;

import java.util.List;
import java.util.Optional;

public interface TrainingTypeRepository {

    List<TrainingType> findAll();

    Optional<TrainingType> findByName(String typeName);
}
