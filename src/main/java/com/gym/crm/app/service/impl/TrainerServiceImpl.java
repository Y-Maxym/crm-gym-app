package com.gym.crm.app.service.impl;

import com.gym.crm.app.entity.Trainer;
import com.gym.crm.app.entity.Training;
import com.gym.crm.app.exception.EntityValidationException;
import com.gym.crm.app.logging.MessageHelper;
import com.gym.crm.app.repository.TrainerRepository;
import com.gym.crm.app.service.TrainerService;
import com.gym.crm.app.service.common.EntityValidator;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

import static com.gym.crm.app.rest.exception.ErrorCode.TRAINER_WITH_ID_NOT_FOUND;
import static com.gym.crm.app.rest.exception.ErrorCode.TRAINER_WITH_USERNAME_NOT_FOUND;
import static com.gym.crm.app.util.Constants.ERROR_TRAINER_WITH_ID_NOT_FOUND;
import static com.gym.crm.app.util.Constants.ERROR_TRAINER_WITH_USERNAME_NOT_FOUND;

@Service
@Setter(onMethod_ = @Autowired)
public class TrainerServiceImpl implements TrainerService {

    private MessageHelper messageHelper;
    private TrainerRepository repository;
    private EntityValidator entityValidator;

    public Trainer findById(Long id) {
        entityValidator.checkId(id);

        return repository.findById(id)
                .orElseThrow(() -> new EntityValidationException(messageHelper.getMessage(ERROR_TRAINER_WITH_ID_NOT_FOUND, id), TRAINER_WITH_ID_NOT_FOUND.getCode()));
    }

    @Override
    public Trainer findByUsername(String username) {
        return repository.findByUsername(username)
                .orElseThrow(() -> new EntityValidationException(messageHelper.getMessage(ERROR_TRAINER_WITH_USERNAME_NOT_FOUND, username), TRAINER_WITH_USERNAME_NOT_FOUND.getCode()));
    }

    @Override
    public List<Training> findTrainingsByCriteria(String username, LocalDate from, LocalDate to, String traineeName, String trainingType) {
        return repository.findTrainingsByCriteria(username, from, to, traineeName, trainingType);
    }

    @Override
    public List<Trainer> getTrainersNotAssignedByTraineeUsername(String username) {
        entityValidator.checkEntity(username);

        return repository.getTrainersNotAssignedByTraineeUsername(username);
    }

    public void save(Trainer trainer) {
        entityValidator.checkEntity(trainer);

        repository.save(trainer);
    }

    public Trainer update(Trainer trainer) {
        entityValidator.checkEntity(trainer);
        entityValidator.checkId(trainer.getId());

        return repository.update(trainer);
    }
}
