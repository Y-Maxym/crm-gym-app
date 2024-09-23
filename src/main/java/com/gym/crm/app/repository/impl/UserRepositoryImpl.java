package com.gym.crm.app.repository.impl;

import com.gym.crm.app.entity.User;
import com.gym.crm.app.repository.UserRepository;
import jakarta.persistence.NoResultException;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UserRepositoryImpl extends CrudRepositoryImpl<User> implements UserRepository {

    public UserRepositoryImpl() {
        super(User.class);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        try {
            User user = execute(entityManager -> {
                return entityManager.createQuery("FROM User u WHERE u.username = :username", User.class)
                        .setParameter("username", username)
                        .getSingleResult();
            });

            return Optional.of(user);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }
}
