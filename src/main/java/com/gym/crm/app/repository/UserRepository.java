package com.gym.crm.app.repository;

import com.gym.crm.app.entity.User;

public abstract class UserRepository extends CrudRepository<User, Long> {

    public UserRepository() {
        super(User.class);
    }
}
