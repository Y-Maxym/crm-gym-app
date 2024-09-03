package com.crm.gym.app.model.service.implementation;

import com.crm.gym.app.model.entity.Trainee;
import com.crm.gym.app.model.repository.EntityDao;
import com.crm.gym.app.model.service.TraineeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TraineeServiceImpl implements TraineeService {

    private final EntityDao<Long, Trainee> repository;

    public Trainee findById(Long id) {
        return repository.findById(id);
    }

    public void save(Trainee trainee) {
        repository.save(trainee);
    }

    public void update(Trainee trainee) {
        repository.update(trainee);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
