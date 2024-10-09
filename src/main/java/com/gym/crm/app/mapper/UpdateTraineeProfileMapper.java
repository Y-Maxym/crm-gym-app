package com.gym.crm.app.mapper;

import com.gym.crm.app.entity.Trainee;
import com.gym.crm.app.entity.User;
import com.gym.crm.app.rest.model.UpdateTraineeProfileRequest;
import com.gym.crm.app.rest.model.UpdateTraineeProfileResponse;
import com.gym.crm.app.service.common.UserProfileService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import static java.util.Objects.isNull;

@Mapper(componentModel = "spring", uses = {UpdateUserProfileMapper.class, TrainingTypeMapper.class, TrainerProfileMapper.class})
public abstract class UpdateTraineeProfileMapper {

    @Autowired
    private UserProfileService userProfileService;

    @Mapping(target = "username", source = "user.username")
    @Mapping(target = "firstName", source = "user.firstName")
    @Mapping(target = "lastName", source = "user.lastName")
    @Mapping(target = "isActive", source = "user.active")
    @Mapping(target = "trainersList", source = "trainers")
    public abstract UpdateTraineeProfileResponse map(Trainee entity);

    public Trainee updateTraineeProfileFromDto(UpdateTraineeProfileRequest dto, Trainee entity) {
        if (dto == null) {
            return entity;
        }

        return entity.toBuilder()
                .dateOfBirth(isNull(dto.getDateOfBirth()) ? entity.getDateOfBirth() : dto.getDateOfBirth())
                .address(isNull(dto.getAddress()) ? entity.getAddress() : dto.getAddress())
                .user(updateUserFromTraineeDto(dto, entity.getUser()))
                .build();
    }

    private User updateUserFromTraineeDto(UpdateTraineeProfileRequest dto, User entity) {
        if (dto == null) {
            return entity;
        }

        String firstName = entity.getFirstName();
        String lastName = entity.getLastName();
        String username = entity.getUsername();

        if (!firstName.equals(dto.getFirstName()) || !lastName.equals(dto.getLastName())) {
            username = userProfileService.generateUsername(dto.getFirstName(), dto.getLastName());
        }

        return entity.toBuilder()
                .username(username)
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .isActive(dto.getIsActive())
                .build();
    }
}
