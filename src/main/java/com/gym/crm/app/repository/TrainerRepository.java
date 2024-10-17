package com.gym.crm.app.repository;

import com.gym.crm.app.entity.Trainer;
import com.gym.crm.app.entity.Training;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface TrainerRepository extends JpaRepository<Trainer, Long> {

    Optional<Trainer> findByUserUsername(String username);

    @Query("""
            SELECT t FROM Training t
            WHERE t.trainer.user.username = :username
            AND (t.trainingDate >= COALESCE(:from, t.trainingDate))
            AND (t.trainingDate <= COALESCE(:to, t.trainingDate))
            AND (:traineeName IS NULL OR t.trainee.user.firstName = :traineeName)
            AND (:trainingType IS NULL OR t.trainingType.trainingTypeName = :trainingType)
            """)
    List<Training> findTrainingsByCriteria(@NotNull String username,
                                           @Nullable LocalDate from,
                                           @Nullable LocalDate to,
                                           @Nullable String traineeName,
                                           @Nullable String trainingType);

    @Query("FROM Trainer t WHERE t NOT IN (" +
            "SELECT t.trainers FROM Trainee t WHERE t.user.username = :username)")
    List<Trainer> getTrainersNotAssignedByTraineeUsername(String username);
}
