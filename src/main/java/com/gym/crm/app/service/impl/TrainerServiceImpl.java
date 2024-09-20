package com.gym.crm.app.service.impl;

import com.gym.crm.app.entity.Trainer;
import com.gym.crm.app.exception.EntityValidationException;
import com.gym.crm.app.logging.MessageHelper;
import com.gym.crm.app.repository.TrainerDao;
import com.gym.crm.app.service.TrainerService;
import com.gym.crm.app.service.common.EntityValidator;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.gym.crm.app.util.Constants.ERROR_TRAINER_WITH_ID_NOT_FOUND;

@Service
@Setter(onMethod_ = @Autowired)
public class TrainerServiceImpl implements TrainerService {

    private MessageHelper messageHelper;
    private TrainerDao repository;
    private EntityValidator entityValidator;

    public Trainer findById(Long id) {
        entityValidator.checkId(id);

        return repository.findById(id)
                .orElseThrow(() -> new EntityValidationException(messageHelper.getMessage(ERROR_TRAINER_WITH_ID_NOT_FOUND, id)));
    }

    public void save(Trainer trainer) {
        entityValidator.checkEntity(trainer);

        repository.save(trainer);
    }

    public void update(Trainer trainer) {
        entityValidator.checkEntity(trainer);
        entityValidator.checkId(trainer.getId());

        repository.update(trainer);
    }
}
