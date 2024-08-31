package com.crm.gym.app.model.service.implementation;

import com.crm.gym.app.model.entity.Trainer;
import com.crm.gym.app.model.repository.TrainerDao;
import com.crm.gym.app.model.service.TrainerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TrainerServiceImpl implements TrainerService {

    private final TrainerDao trainerDao;

    public Trainer findById(Long id) {
        return trainerDao.findById(id);
    }

    public void save(Trainer trainer) {
        trainerDao.save(trainer);
    }

    public void update(Trainer trainer) {
        trainerDao.update(trainer);
    }
}