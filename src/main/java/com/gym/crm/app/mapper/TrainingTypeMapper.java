package com.gym.crm.app.mapper;

import com.gym.crm.app.entity.TrainingType;
import com.gym.crm.app.exception.EntityValidationException;
import com.gym.crm.app.logging.MessageHelper;
import com.gym.crm.app.repository.TrainingTypeRepository;
import lombok.Setter;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

import static com.gym.crm.app.util.Constants.ERROR_TRAINING_TYPE_WITH_NAME_NOT_FOUND;

@Mapper(componentModel = "spring")
@Setter(onMethod_ = @Autowired)
public abstract class TrainingTypeMapper {

    protected MessageHelper messageHelper;
    protected TrainingTypeRepository repository;

    public String map(TrainingType trainingType) {
        return trainingType == null ? null : trainingType.getTrainingTypeName();
    }

    public TrainingType map(String trainingTypeName) {
        return repository.findByName(trainingTypeName)
                .orElseThrow(() -> new EntityValidationException(messageHelper.getMessage(ERROR_TRAINING_TYPE_WITH_NAME_NOT_FOUND, trainingTypeName)));
    }
}
