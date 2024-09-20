package com.gym.crm.app.repository;

import com.gym.crm.app.entity.Trainer;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Setter(onMethod_ = @Autowired)
public class TrainerDao extends CrudDao<Long, Trainer> {

    public TrainerDao() {
        super(Trainer.class);
    }

    public Optional<Trainer> findByUsername(String username) {
        List<Trainer> trainers = executeForList(entityManager -> {
            return entityManager.createQuery("FROM Trainer t WHERE t.user.username = :username", Trainer.class)
                    .setParameter("username", username)
                    .getResultList();
        });

        return trainers.isEmpty() ? Optional.empty() : Optional.of(trainers.get(0));
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
