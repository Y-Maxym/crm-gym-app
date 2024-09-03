package com.crm.gym.app.model.service.implementation;

import com.crm.gym.app.model.entity.Training;
import com.crm.gym.app.model.repository.EntityDao;
import com.crm.gym.app.model.service.TrainingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TrainingServiceImpl implements TrainingService {

    private final EntityDao<Long, Training> repository;

    public Training findById(Long id) {
        return repository.findById(id);
    }

    public void save(Training training) {
        repository.save(training);
    }
}
