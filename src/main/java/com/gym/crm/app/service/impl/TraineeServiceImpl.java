package com.gym.crm.app.service.impl;

import com.gym.crm.app.entity.Trainee;
import com.gym.crm.app.exception.EntityValidationException;
import com.gym.crm.app.logging.MessageHelper;
import com.gym.crm.app.repository.TraineeRepository;
import com.gym.crm.app.service.TraineeService;
import com.gym.crm.app.service.common.EntityValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.gym.crm.app.rest.exception.ErrorCode.TRAINEE_WITH_ID_NOT_FOUND;
import static com.gym.crm.app.rest.exception.ErrorCode.TRAINEE_WITH_USERNAME_NOT_FOUND;
import static com.gym.crm.app.util.Constants.ERROR_TRAINEE_WITH_ID_NOT_FOUND;
import static com.gym.crm.app.util.Constants.ERROR_TRAINEE_WITH_USERNAME_NOT_FOUND;
import static com.gym.crm.app.util.Constants.WARN_TRAINEE_WITH_ID_NOT_FOUND;
import static com.gym.crm.app.util.Constants.WARN_TRAINEE_WITH_USERNAME_NOT_FOUND;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TraineeServiceImpl implements TraineeService {

    private final MessageHelper messageHelper;
    private final TraineeRepository repository;
    private final EntityValidator entityValidator;

    @Override
    public Trainee findById(Long id) {
        entityValidator.checkId(id);

        return repository.findById(id)
                .orElseThrow(() -> new EntityValidationException(messageHelper.getMessage(ERROR_TRAINEE_WITH_ID_NOT_FOUND, id), TRAINEE_WITH_ID_NOT_FOUND.getCode()));
    }

    @Override
    public Trainee findByUsername(String username) {
        return repository.findByUserUsername(username)
                .orElseThrow(() -> new EntityValidationException(messageHelper.getMessage(ERROR_TRAINEE_WITH_USERNAME_NOT_FOUND, username), TRAINEE_WITH_USERNAME_NOT_FOUND.getCode()));
    }

    @Override
    @Transactional
    public void save(Trainee trainee) {
        entityValidator.checkEntity(trainee);

        repository.save(trainee);
    }

    @Override
    @Transactional
    public Trainee update(Trainee trainee) {
        entityValidator.checkEntity(trainee);
        entityValidator.checkId(trainee.getId());

        return repository.save(trainee);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        entityValidator.checkId(id);

        if (repository.findById(id).isEmpty()) {
            log.warn(messageHelper.getMessage(WARN_TRAINEE_WITH_ID_NOT_FOUND, id));
        }

        repository.deleteById(id);
    }

    @Override
    @Transactional
    public void deleteByUsername(String username) {
        entityValidator.checkEntity(username);

        if (repository.findByUserUsername(username).isEmpty()) {
            log.warn(messageHelper.getMessage(WARN_TRAINEE_WITH_USERNAME_NOT_FOUND, username));
        }

        repository.deleteByUserUsername(username);
    }
}
