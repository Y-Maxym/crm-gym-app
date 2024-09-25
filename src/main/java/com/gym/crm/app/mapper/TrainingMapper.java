package com.gym.crm.app.mapper;

import com.gym.crm.app.dto.request.TrainingRequest;
import com.gym.crm.app.dto.response.TrainingResponse;
import com.gym.crm.app.entity.Trainee;
import com.gym.crm.app.entity.Trainer;
import com.gym.crm.app.entity.Training;
import com.gym.crm.app.service.TraineeService;
import com.gym.crm.app.service.TrainerService;
import lombok.Setter;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;

@Mapper(componentModel = "spring", uses = {TrainerProfileMapper.class, TraineeProfileMapper.class, TrainingTypeMapper.class})
@Setter(onMethod_ = @Autowired)
public abstract class TrainingMapper {

    protected TrainerService trainerService;
    protected TraineeService traineeService;

    @Mapping(target = "traineeUsername", source = "trainee.user.username")
    @Mapping(target = "trainerUsername", source = "trainer.user.username")
    public abstract TrainingResponse map(Training entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "trainer", expression = "java(mapTrainer(dto.trainerUsername()))")
    @Mapping(target = "trainee", expression = "java(mapTrainee(dto.traineeUsername()))")
    public abstract Training map(TrainingRequest dto);

    protected Trainer mapTrainer(String trainerUsername) {
        return trainerService.findByUsername(trainerUsername);
    }

    protected Trainee mapTrainee(String traineeUsername) {
        return traineeService.findByUsername(traineeUsername);
    }

    public abstract Set<TrainingResponse> mapList(Set<Training> entities);
}
