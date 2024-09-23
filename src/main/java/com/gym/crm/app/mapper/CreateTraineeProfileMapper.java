package com.gym.crm.app.mapper;

import com.gym.crm.app.dto.request.trainee.CreateTraineeProfileRequest;
import com.gym.crm.app.dto.response.trainee.CreateTraineeProfileResponse;
import com.gym.crm.app.entity.Trainee;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = CreateUserProfileMapper.class)
public interface CreateTraineeProfileMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "trainers", ignore = true)
    @Mapping(target = "trainings", ignore = true)
    Trainee map(CreateTraineeProfileRequest dto);

    CreateTraineeProfileResponse map(Trainee entity);
}
