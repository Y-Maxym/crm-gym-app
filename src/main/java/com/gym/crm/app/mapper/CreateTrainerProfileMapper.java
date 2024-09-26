package com.gym.crm.app.mapper;

import com.gym.crm.app.dto.request.CreateTrainerProfileRequest;
import com.gym.crm.app.dto.response.CreateTrainerProfileResponse;
import com.gym.crm.app.entity.Trainer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {CreateUserProfileMapper.class, TrainingTypeMapper.class})
public interface CreateTrainerProfileMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "trainees", ignore = true)
    @Mapping(target = "trainings", ignore = true)
    Trainer map(CreateTrainerProfileRequest dto);

    CreateTrainerProfileResponse map(Trainer entity);

}
