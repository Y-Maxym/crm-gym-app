package com.gym.crm.app.service.impl;

import com.gym.crm.app.entity.Training;
import com.gym.crm.app.exception.EntityValidationException;
import com.gym.crm.app.logging.MessageHelper;
import com.gym.crm.app.repository.TrainingRepository;
import com.gym.crm.app.service.TrainingService;
import com.gym.crm.app.service.common.EntityValidator;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.gym.crm.app.util.Constants.ERROR_TRAINING_WITH_ID_NOT_FOUND;

@Service
@Setter(onMethod_ = @Autowired)
public class TrainingServiceImpl implements TrainingService {

    private MessageHelper messageHelper;
    private TrainingRepository repository;
    private EntityValidator entityValidator;

    public Training findById(Long id) {
        entityValidator.checkId(id);

        return repository.findById(id)
                .orElseThrow(() -> new EntityValidationException(messageHelper.getMessage(ERROR_TRAINING_WITH_ID_NOT_FOUND, id)));
    }

    public void save(Training training) {
        entityValidator.checkEntity(training);

        repository.save(training);
    }
}
