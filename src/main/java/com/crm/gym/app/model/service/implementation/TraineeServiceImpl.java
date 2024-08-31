package com.crm.gym.app.model.service.implementation;

import com.crm.gym.app.model.entity.Trainee;
import com.crm.gym.app.model.repository.TraineeDao;
import com.crm.gym.app.model.service.TraineeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TraineeServiceImpl implements TraineeService {

    private final TraineeDao traineeDao;

    public Trainee findById(Long id) {
        return traineeDao.findById(id);
    }

    public void save(Trainee trainee) {
        traineeDao.save(trainee);
    }

    public void update(Trainee trainee) {
        traineeDao.update(trainee);
    }

    public void deleteById(Long id) {
        traineeDao.deleteById(id);
    }
}
