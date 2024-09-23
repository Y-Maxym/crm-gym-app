package com.gym.crm.app.mapper;

import com.gym.crm.app.dto.request.user.CreateUserProfileRequest;
import com.gym.crm.app.dto.response.user.CreateUserProfileResponse;
import com.gym.crm.app.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CreateUserProfileMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "username", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "isActive", ignore = true)
    User map(CreateUserProfileRequest dto);

    CreateUserProfileResponse map(User user);
}
