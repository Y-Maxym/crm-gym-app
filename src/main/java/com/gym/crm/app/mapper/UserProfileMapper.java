package com.gym.crm.app.mapper;

import com.gym.crm.app.dto.request.UserProfileRequest;
import com.gym.crm.app.dto.response.UserProfileResponse;
import com.gym.crm.app.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserProfileMapper {

    UserProfileResponse map(User entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "isActive", ignore = true)
    User map(UserProfileRequest dto);
}
