package com.gym.crm.app.service.impl;

import com.gym.crm.app.entity.Training;
import com.gym.crm.app.exception.EntityValidationException;
import com.gym.crm.app.logging.MessageHelper;
import com.gym.crm.app.repository.EntityDao;
import com.gym.crm.app.service.EntityExceptionHelper;
import com.gym.crm.app.service.TrainingService;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.gym.crm.app.util.Constants.ERROR_TRAINING_WITH_ID_NOT_FOUND;

@Service
@Setter(onMethod_ = @Autowired)
public class TrainingServiceImpl implements TrainingService {

    private MessageHelper messageHelper;
    private EntityDao<Long, Training> repository;
    private EntityExceptionHelper exceptionHelper;

    public Training findById(Long id) {
        exceptionHelper.checkId(id);

        return repository.findById(id)
                .orElseThrow(() -> new EntityValidationException(messageHelper.getMessage(ERROR_TRAINING_WITH_ID_NOT_FOUND, id)));
    }

    public void save(Training training) {
        exceptionHelper.checkEntity(training);

        repository.save(training);
    }
}
