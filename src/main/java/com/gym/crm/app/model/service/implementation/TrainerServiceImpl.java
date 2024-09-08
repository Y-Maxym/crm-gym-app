package com.gym.crm.app.model.service.implementation;

import com.gym.crm.app.exception.EntityNotFoundException;
import com.gym.crm.app.model.entity.Trainer;
import com.gym.crm.app.model.repository.EntityDao;
import com.gym.crm.app.model.service.TrainerService;
import com.gym.crm.app.util.MessageUtils;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.gym.crm.app.util.Constants.ERROR_TRAINER_WITH_ID_NOT_FOUND;

@Service
@Setter(onMethod_ = @Autowired)
public class TrainerServiceImpl implements TrainerService {

    private MessageUtils messageUtils;
    private EntityDao<Long, Trainer> repository;

    public Trainer findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(messageUtils.getMessage(ERROR_TRAINER_WITH_ID_NOT_FOUND, id)));
    }

    public void save(Trainer trainer) {
        repository.save(trainer);
    }

    public void update(Trainer trainer) {
        repository.update(trainer);
    }
}