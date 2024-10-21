package com.gym.crm.app.service;

import com.gym.crm.app.entity.Training;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface TrainingService {

    Training findById(Long id);

    List<Training> findByCriteria(Specification<Training> specification);

    void save(Training training);

}
