package com.gym.crm.app.mapper;

import com.gym.crm.app.dto.request.training.TrainingRequest;
import com.gym.crm.app.dto.response.training.TrainingResponse;
import com.gym.crm.app.entity.Training;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Set;

@Mapper(componentModel = "spring", uses = {TrainerProfileMapper.class, TraineeProfileMapper.class, TrainingTypeMapper.class})
public interface TrainingMapper {

    TrainingResponse map(Training entity);

    @Mapping(target = "id", ignore = true)
    Training map(TrainingRequest dto);

    Set<TrainingResponse> mapList(Set<Training> entities);
}
