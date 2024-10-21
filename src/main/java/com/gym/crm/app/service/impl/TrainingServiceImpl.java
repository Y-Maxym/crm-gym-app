package com.gym.crm.app.service.impl;

import com.gym.crm.app.entity.Training;
import com.gym.crm.app.entity.TrainingSearchFilter;
import com.gym.crm.app.exception.EntityValidationException;
import com.gym.crm.app.logging.MessageHelper;
import com.gym.crm.app.repository.TrainingRepository;
import com.gym.crm.app.service.TrainingService;
import com.gym.crm.app.service.common.EntityValidator;
import com.gym.crm.app.service.spectification.TraineeTrainingSpecification;
import com.gym.crm.app.service.spectification.TrainerTrainingSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.gym.crm.app.rest.exception.ErrorCode.TRAINING_WITH_ID_NOT_FOUND;
import static com.gym.crm.app.util.Constants.ERROR_TRAINING_WITH_ID_NOT_FOUND;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TrainingServiceImpl implements TrainingService {

    private final MessageHelper messageHelper;
    private final TrainingRepository repository;
    private final EntityValidator validator;

    @Override
    public Training findById(Long id) {
        validator.checkId(id);

        return repository.findById(id)
                .orElseThrow(() -> new EntityValidationException(messageHelper.getMessage(ERROR_TRAINING_WITH_ID_NOT_FOUND, id), TRAINING_WITH_ID_NOT_FOUND.getCode()));
    }

    @Override
    public List<Training> findTraineeTrainingByCriteria(TrainingSearchFilter searchFilter) {
        validator.checkIfTraineeExist(searchFilter.getUsername());

        Specification<Training> specification = TraineeTrainingSpecification.findByCriteria(searchFilter);

        return repository.findAll(specification);
    }

    @Override
    public List<Training> findTrainerTrainingByCriteria(TrainingSearchFilter searchFilter) {
        validator.checkIfTrainerExist(searchFilter.getUsername());

        Specification<Training> specification = TrainerTrainingSpecification.findByCriteria(searchFilter);

        return repository.findAll(specification);
    }

    @Override
    @Transactional
    public void save(Training training) {
        validator.checkEntity(training);

        repository.save(training);
    }
}
