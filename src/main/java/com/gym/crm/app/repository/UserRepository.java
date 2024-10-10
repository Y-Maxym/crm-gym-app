package com.gym.crm.app.repository;

import com.gym.crm.app.entity.User;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User> {

    Optional<User> findByUsername(String username);

    Long getNextSerialNumber();

}
