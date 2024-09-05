package com.crm.gym.app.model.service.implementation;

import com.crm.gym.app.exception.EntityNotFoundException;
import com.crm.gym.app.model.entity.Trainee;
import com.crm.gym.app.model.repository.EntityDao;
import com.crm.gym.app.model.service.TraineeService;
import com.crm.gym.app.util.MessageUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.crm.gym.app.util.Constants.ERROR_TRAINEE_WITH_ID_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class TraineeServiceImpl implements TraineeService {

    private final MessageUtils messageUtils;
    private final EntityDao<Long, Trainee> repository;

    public Trainee findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(messageUtils.getMessage(ERROR_TRAINEE_WITH_ID_NOT_FOUND, id)));
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
