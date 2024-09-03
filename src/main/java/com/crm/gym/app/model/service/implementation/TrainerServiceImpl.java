package com.crm.gym.app.model.service.implementation;

import com.crm.gym.app.model.entity.Trainer;
import com.crm.gym.app.model.repository.EntityDao;
import com.crm.gym.app.model.service.TrainerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TrainerServiceImpl implements TrainerService {

    private final EntityDao<Long, Trainer> repository;

    public Trainer findById(Long id) {
        return repository.findById(id);
    }

    public void save(Trainer trainer) {
        repository.save(trainer);
    }

    public void update(Trainer trainer) {
        repository.update(trainer);
    }
}