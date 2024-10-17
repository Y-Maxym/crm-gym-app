package com.gym.crm.app.repository;

import com.gym.crm.app.entity.Trainee;
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
public interface TraineeRepository extends JpaRepository<Trainee, Long> {

    Optional<Trainee> findByUserUsername(String username);

    @Query("""
            SELECT t FROM Training t
            WHERE t.trainee.user.username = :username
            AND (t.trainingDate >= COALESCE(:from, t.trainingDate))
            AND (t.trainingDate <= COALESCE(:to, t.trainingDate))
            AND (:trainerName IS NULL OR t.trainer.user.firstName = :trainerName)
            AND (:trainingType IS NULL OR t.trainingType.trainingTypeName = :trainingType)
            """)
    List<Training> findTrainingsByCriteria(@NotNull String username,
                                           @Nullable LocalDate from,
                                           @Nullable LocalDate to,
                                           @Nullable String trainerName,
                                           @Nullable String trainingType);

    void deleteByUserUsername(String username);
}
