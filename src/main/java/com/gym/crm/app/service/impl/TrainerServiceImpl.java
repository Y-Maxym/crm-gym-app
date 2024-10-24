package com.gym.crm.app.service.impl;

import com.gym.crm.app.entity.Trainer;
import com.gym.crm.app.exception.EntityValidationException;
import com.gym.crm.app.logging.MessageHelper;
import com.gym.crm.app.repository.TrainerRepository;
import com.gym.crm.app.service.TrainerService;
import com.gym.crm.app.service.common.EntityValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.gym.crm.app.rest.exception.ErrorCode.TRAINER_WITH_ID_NOT_FOUND;
import static com.gym.crm.app.rest.exception.ErrorCode.TRAINER_WITH_USERNAME_NOT_FOUND;
import static com.gym.crm.app.util.Constants.ERROR_TRAINER_WITH_ID_NOT_FOUND;
import static com.gym.crm.app.util.Constants.ERROR_TRAINER_WITH_USERNAME_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class TrainerServiceImpl implements TrainerService {

    private final MessageHelper messageHelper;
    private final TrainerRepository repository;
    private final EntityValidator entityValidator;

    @Override
    @Transactional(readOnly = true)
    public Trainer findById(Long id) {
        entityValidator.checkId(id);

        return repository.findById(id)
                .orElseThrow(() -> new EntityValidationException(messageHelper.getMessage(ERROR_TRAINER_WITH_ID_NOT_FOUND, id), TRAINER_WITH_ID_NOT_FOUND.getCode()));
    }

    @Override
    @Transactional(readOnly = true)
    public Trainer findByUsername(String username) {
        return repository.findByUserUsername(username)
                .orElseThrow(() -> new EntityValidationException(messageHelper.getMessage(ERROR_TRAINER_WITH_USERNAME_NOT_FOUND, username), TRAINER_WITH_USERNAME_NOT_FOUND.getCode()));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Trainer> getTrainersNotAssignedByTraineeUsername(String username) {
        entityValidator.checkEntity(username);

        return repository.getTrainersNotAssignedByTraineeUsername(username);
    }

    @Override
    @Transactional
    public void save(Trainer trainer) {
        entityValidator.checkEntity(trainer);

        repository.save(trainer);
    }

    @Override
    @Transactional
    public Trainer update(Trainer trainer) {
        entityValidator.checkEntity(trainer);
        entityValidator.checkId(trainer.getId());

        return repository.save(trainer);
    }
}
