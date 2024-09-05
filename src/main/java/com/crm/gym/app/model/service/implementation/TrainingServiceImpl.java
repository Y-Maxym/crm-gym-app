package com.crm.gym.app.model.service.implementation;

import com.crm.gym.app.exception.EntityNotFoundException;
import com.crm.gym.app.model.entity.Training;
import com.crm.gym.app.model.repository.EntityDao;
import com.crm.gym.app.model.service.TrainingService;
import com.crm.gym.app.util.MessageUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.crm.gym.app.util.Constants.ERROR_TRAINING_WITH_ID_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class TrainingServiceImpl implements TrainingService {

    private final MessageUtils messageUtils;
    private final EntityDao<Long, Training> repository;

    public Training findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(messageUtils.getMessage(ERROR_TRAINING_WITH_ID_NOT_FOUND, id)));
    }

    public void save(Training training) {
        repository.save(training);
    }
}
