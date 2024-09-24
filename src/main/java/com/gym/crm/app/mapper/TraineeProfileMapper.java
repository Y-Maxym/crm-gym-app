package com.gym.crm.app.mapper;

import com.gym.crm.app.dto.request.TraineeProfileRequest;
import com.gym.crm.app.dto.response.TraineeProfileResponse;
import com.gym.crm.app.entity.Trainee;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = UserProfileMapper.class)
public interface TraineeProfileMapper {

    TraineeProfileResponse map(Trainee entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "trainers", ignore = true)
    @Mapping(target = "trainings", ignore = true)
    Trainee map(TraineeProfileRequest dto);
}
