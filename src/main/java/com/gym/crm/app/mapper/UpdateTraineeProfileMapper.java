package com.gym.crm.app.mapper;

import com.gym.crm.app.entity.Trainee;
import com.gym.crm.app.rest.model.UpdateTraineeProfileRequest;
import com.gym.crm.app.rest.model.UpdateTraineeProfileResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {UpdateUserProfileMapper.class, TrainingTypeMapper.class, TrainerProfileMapper.class})
public interface UpdateTraineeProfileMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "trainers", ignore = true)
    @Mapping(target = "trainings", ignore = true)
    @Mapping(target = "user", source = ".")
    Trainee map(UpdateTraineeProfileRequest dto);

    @Mapping(target = "username", source = "user.username")
    @Mapping(target = "firstName", source = "user.firstName")
    @Mapping(target = "lastName", source = "user.lastName")
    @Mapping(target = "isActive", source = "user.active")
    @Mapping(target = "trainersList", source = "trainers")
    UpdateTraineeProfileResponse map(Trainee entity);
}
