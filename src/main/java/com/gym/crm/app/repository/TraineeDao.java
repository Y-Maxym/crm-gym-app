package com.gym.crm.app.repository;

import com.gym.crm.app.entity.Trainee;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Setter(onMethod_ = @Autowired)
public class TraineeDao extends CrudDao<Long, Trainee> {

    public TraineeDao() {
        super(Trainee.class);
    }

    public Optional<Trainee> findByUsername(String username) {
        List<Trainee> trainees = executeForList(entityManager -> {
            return entityManager.createQuery("FROM Trainee t WHERE t.user.username = :username", Trainee.class)
                    .setParameter("username", username)
                    .getResultList();
        });

        return trainees.isEmpty() ? Optional.empty() : Optional.of(trainees.get(0));
    }

    public void deleteByUsername(String username) {
        Optional<Trainee> trainee = findByUsername(username);

        trainee.ifPresent(value -> deleteById(value.getId()));
    }
}
