package com.gym.crm.app.repository.impl;

import com.gym.crm.app.entity.Trainee;
import com.gym.crm.app.repository.TraineeRepository;
import jakarta.persistence.NoResultException;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@Setter(onMethod_ = @Autowired)
public class TraineeRepositoryImpl extends TraineeRepository {

    public Optional<Trainee> findByUsername(String username) {
        try {
            Trainee trainee = execute(entityManager -> {
                return entityManager.createQuery("FROM Trainee t WHERE t.user.username = :username", Trainee.class)
                        .setParameter("username", username)
                        .getSingleResult();
            });

            return Optional.of(trainee);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    public void deleteByUsername(String username) {
        findByUsername(username)
                .ifPresent(trainee -> deleteById(trainee.getId()));
    }
}
