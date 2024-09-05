package com.crm.gym.app.model.service.implementation;

import com.crm.gym.app.exception.EntityNotFoundException;
import com.crm.gym.app.model.entity.Trainer;
import com.crm.gym.app.model.repository.EntityDao;
import com.crm.gym.app.model.service.TrainerService;
import com.crm.gym.app.util.MessageUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.crm.gym.app.util.Constants.ERROR_TRAINER_WITH_ID_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class TrainerServiceImpl implements TrainerService {

    private final MessageUtils messageUtils;
    private final EntityDao<Long, Trainer> repository;

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