package com.gym.crm.app.model.service.implementation;

import com.gym.crm.app.exception.EntityNotFoundException;
import com.gym.crm.app.model.entity.Training;
import com.gym.crm.app.model.repository.EntityDao;
import com.gym.crm.app.model.service.TrainingService;
import com.gym.crm.app.util.MessageUtils;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.gym.crm.app.util.Constants.ERROR_TRAINING_WITH_ID_NOT_FOUND;

@Service
@Setter(onMethod_ = @Autowired)
public class TrainingServiceImpl implements TrainingService {

    private MessageUtils messageUtils;
    private EntityDao<Long, Training> repository;

    public Training findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(messageUtils.getMessage(ERROR_TRAINING_WITH_ID_NOT_FOUND, id)));
    }

    public void save(Training training) {
        repository.saveOrUpdate(training);
    }
}
