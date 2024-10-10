package com.gym.crm.app.mapper;

import com.gym.crm.app.entity.TrainingType;
import com.gym.crm.app.exception.EntityValidationException;
import com.gym.crm.app.logging.MessageHelper;
import com.gym.crm.app.repository.TrainingTypeRepository;
import com.gym.crm.app.rest.model.GetTrainingTypeResponse;
import lombok.Setter;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import static com.gym.crm.app.util.Constants.ERROR_TRAINING_TYPE_WITH_NAME_NOT_FOUND;

@Mapper(componentModel = "spring")
@Setter(onMethod_ = @Autowired)
public abstract class TrainingTypeMapper {

    protected MessageHelper messageHelper;
    protected TrainingTypeRepository repository;

    @Mapping(target = "trainingTypeId", source = "id")
    @Mapping(target = "trainingType", source = "trainingTypeName")
    public abstract GetTrainingTypeResponse mapToTrainingTypeResponse(TrainingType trainingType);

    public String map(TrainingType trainingType) {
        return trainingType == null ? null : trainingType.getTrainingTypeName();
    }

    public TrainingType map(String trainingType) {
        return repository.findByName(trainingType)
                .orElseThrow(() -> new EntityValidationException(messageHelper.getMessage(ERROR_TRAINING_TYPE_WITH_NAME_NOT_FOUND, trainingType)));
    }
}
