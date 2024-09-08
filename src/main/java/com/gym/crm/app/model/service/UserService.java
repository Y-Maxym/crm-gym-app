package com.gym.crm.app.model.service;

import com.gym.crm.app.model.entity.User;

public interface UserService {

    User findById(Long id);

    void save(User user);

    void update(User user);

    void deleteById(Long id);
}
