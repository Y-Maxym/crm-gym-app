package com.gym.crm.app.repository.impl;

import com.gym.crm.app.entity.User;
import com.gym.crm.app.repository.UserRepository;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepositoryImpl extends CrudRepositoryImpl<User> implements UserRepository {

    public UserRepositoryImpl() {
        super(User.class);
    }
}
