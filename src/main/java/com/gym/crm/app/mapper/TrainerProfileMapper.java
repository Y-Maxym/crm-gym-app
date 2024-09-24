package com.gym.crm.app.mapper;

import com.gym.crm.app.dto.request.TrainerProfileRequest;
import com.gym.crm.app.dto.response.TrainerProfileResponse;
import com.gym.crm.app.entity.Trainer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {UserProfileMapper.class, TrainingTypeMapper.class})
public interface TrainerProfileMapper {

    TrainerProfileResponse map(Trainer entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "trainees", ignore = true)
    @Mapping(target = "trainings", ignore = true)
    Trainer map(TrainerProfileRequest dto);

    List<TrainerProfileResponse> mapList(List<Trainer> entities);
}
