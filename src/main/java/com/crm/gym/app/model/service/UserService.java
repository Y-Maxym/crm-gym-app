package com.crm.gym.app.model.service;

import com.crm.gym.app.model.entity.User;

public interface UserService {

    User findById(Long id);

    void save(User user);

    void update(User user);

    void deleteById(Long id);
}
