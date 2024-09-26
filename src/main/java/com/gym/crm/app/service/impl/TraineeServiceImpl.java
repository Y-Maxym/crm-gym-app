package com.gym.crm.app.service.impl;

import com.gym.crm.app.entity.Trainee;
import com.gym.crm.app.entity.Training;
import com.gym.crm.app.exception.EntityValidationException;
import com.gym.crm.app.logging.MessageHelper;
import com.gym.crm.app.repository.TraineeRepository;
import com.gym.crm.app.service.TraineeService;
import com.gym.crm.app.service.common.EntityValidator;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Set;

import static com.gym.crm.app.util.Constants.ERROR_TRAINEE_WITH_ID_NOT_FOUND;
import static com.gym.crm.app.util.Constants.ERROR_TRAINEE_WITH_USERNAME_NOT_FOUND;
import static com.gym.crm.app.util.Constants.WARN_TRAINEE_WITH_ID_NOT_FOUND;
import static com.gym.crm.app.util.Constants.WARN_TRAINEE_WITH_USERNAME_NOT_FOUND;

@Slf4j
@Service
@Setter(onMethod_ = @Autowired)
public class TraineeServiceImpl implements TraineeService {

    private MessageHelper messageHelper;
    private TraineeRepository repository;
    private EntityValidator entityValidator;

    public Trainee findById(Long id) {
        entityValidator.checkId(id);

        return repository.findById(id)
                .orElseThrow(() -> new EntityValidationException(messageHelper.getMessage(ERROR_TRAINEE_WITH_ID_NOT_FOUND, id)));
    }

    @Override
    public Trainee findByUsername(String username) {
        return repository.findByUsername(username)
                .orElseThrow(() -> new EntityValidationException(messageHelper.getMessage(ERROR_TRAINEE_WITH_USERNAME_NOT_FOUND, username)));
    }

    @Override
    public Set<Training> findTrainingsByCriteria(String username, LocalDate from, LocalDate to, String trainerName, String trainingType) {
        return repository.findTrainingsByCriteria(username, from, to, trainerName, trainingType);
    }

    public void save(Trainee trainee) {
        entityValidator.checkEntity(trainee);

        repository.save(trainee);
    }

    public void update(Trainee trainee) {
        entityValidator.checkEntity(trainee);
        entityValidator.checkId(trainee.getId());

        repository.update(trainee);
    }

    public void deleteById(Long id) {
        entityValidator.checkId(id);

        if (repository.findById(id).isEmpty()) {
            log.warn(messageHelper.getMessage(WARN_TRAINEE_WITH_ID_NOT_FOUND, id));
        }

        repository.deleteById(id);
    }

    @Override
    public void deleteByUsername(String username) {
        entityValidator.checkEntity(username);

        if (repository.findByUsername(username).isEmpty()) {
            log.warn(messageHelper.getMessage(WARN_TRAINEE_WITH_USERNAME_NOT_FOUND, username));
        }

        repository.deleteByUsername(username);
    }
}
