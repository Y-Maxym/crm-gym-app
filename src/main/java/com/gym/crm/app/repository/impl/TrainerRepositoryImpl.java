package com.gym.crm.app.repository.impl;

import com.gym.crm.app.entity.Trainer;
import com.gym.crm.app.repository.TrainerRepository;
import jakarta.persistence.NoResultException;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class TrainerRepositoryImpl extends CrudRepositoryImpl<Trainer> implements TrainerRepository {

    public TrainerRepositoryImpl() {
        super(Trainer.class);
    }

    public Optional<Trainer> findByUsername(String username) {
        try {
            Trainer trainer = execute(entityManager -> {
                return entityManager.createQuery("FROM Trainer t WHERE t.user.username = :username", Trainer.class)
                        .setParameter("username", username)
                        .getSingleResult();
            });

            return Optional.ofNullable(trainer);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    public List<Trainer> getTrainersNotAssignedByTraineeUsername(String username) {
        return executeForList(entityManager -> {
            return entityManager.createQuery("FROM Trainer t WHERE t NOT IN (" +
                            "SELECT t.trainers FROM Trainee t WHERE t.user.username = :username)", Trainer.class)
                    .setParameter("username", username)
                    .getResultList();
        });
    }
}
