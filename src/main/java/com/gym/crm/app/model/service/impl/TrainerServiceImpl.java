package com.gym.crm.app.model.service.impl;

import com.gym.crm.app.exception.EntityException;
import com.gym.crm.app.logging.MessageHelper;
import com.gym.crm.app.model.entity.Trainer;
import com.gym.crm.app.model.repository.EntityDao;
import com.gym.crm.app.model.service.EntityExceptionHelper;
import com.gym.crm.app.model.service.TrainerService;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.gym.crm.app.util.Constants.ERROR_TRAINER_WITH_ID_NOT_FOUND;

@Service
@Setter(onMethod_ = @Autowired)
public class TrainerServiceImpl implements TrainerService {

    private MessageHelper messageHelper;
    private EntityDao<Long, Trainer> repository;
    private EntityExceptionHelper exceptionHelper;

    public Trainer findById(Long id) {
        exceptionHelper.checkId(id);

        return repository.findById(id)
                .orElseThrow(() -> new EntityException(messageHelper.getMessage(ERROR_TRAINER_WITH_ID_NOT_FOUND, id)));
    }

    public void save(Trainer trainer) {
        exceptionHelper.checkEntity(trainer);

        repository.save(trainer);
    }

    public void update(Trainer trainer) {
        exceptionHelper.checkEntity(trainer);
        exceptionHelper.checkId(trainer.getId());

        repository.update(trainer);
    }
}