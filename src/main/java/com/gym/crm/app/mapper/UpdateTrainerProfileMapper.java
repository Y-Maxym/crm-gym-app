package com.gym.crm.app.mapper;

import com.gym.crm.app.entity.Trainer;
import com.gym.crm.app.rest.model.UpdateTrainerProfileRequest;
import com.gym.crm.app.rest.model.UpdateTrainerProfileResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {UpdateUserProfileMapper.class, TrainerTraineeProfileMapper.class, TrainingTypeMapper.class})
public interface UpdateTrainerProfileMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "trainees", ignore = true)
    @Mapping(target = "trainings", ignore = true)
    @Mapping(target = "user", source = ".")
    Trainer map(UpdateTrainerProfileRequest dto);

    @Mapping(target = "username", source = "user.username")
    @Mapping(target = "firstName", source = "user.firstName")
    @Mapping(target = "lastName", source = "user.lastName")
    @Mapping(target = "isActive", source = "user.active")
    @Mapping(target = "traineesList", source = "trainees")
    UpdateTrainerProfileResponse map(Trainer entity);
}
