package com.gym.crm.app.repository.impl;

import com.gym.crm.app.entity.Trainee;
import com.gym.crm.app.entity.Trainer;
import com.gym.crm.app.entity.Training;
import com.gym.crm.app.entity.TrainingType;
import com.gym.crm.app.entity.User;
import com.gym.crm.app.repository.TrainerRepository;
import jakarta.persistence.NoResultException;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static java.util.Objects.isNull;

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

    @Override
    public List<Training> findTrainingsByCriteria(@NotNull String username, LocalDate from, LocalDate to, String traineeName,
                                                  String trainingType) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Training> query = builder.createQuery(Training.class);
        Root<Training> root = query.from(Training.class);

        Set<Predicate> predicates = new HashSet<>();

        if (!isNull(username) && !username.isBlank()) {
            Join<Training, Trainee> joinTraining = root.join("trainer");
            Join<Trainee, User> joinUser = joinTraining.join("user");
            predicates.add(builder.equal(joinUser.get("username"), username));
        }

        if (!isNull(from)) {
            predicates.add(builder.greaterThanOrEqualTo(root.get("trainingDate"), from));
        }

        if (!isNull(to)) {
            predicates.add(builder.lessThanOrEqualTo(root.get("trainingDate"), to));
        }

        if (!isNull(traineeName) && !traineeName.isBlank()) {
            Join<Training, Trainee> joinTraining = root.join("trainee");
            Join<Trainee, User> joinUser = joinTraining.join("user");
            predicates.add(builder.equal(joinUser.get("firstName"), traineeName));
        }

        if (!isNull(trainingType) && !trainingType.isBlank()) {
            Join<Training, TrainingType> joinTrainingType = root.join("trainingType");
            predicates.add(builder.equal(joinTrainingType.get("trainingTypeName"), trainingType));
        }

        query.select(root).where(predicates.toArray(new Predicate[0]));

        return Collections.unmodifiableList(entityManager.createQuery(query).getResultList());
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
