package com.gym.crm.app.spectification;

import com.gym.crm.app.entity.Training;
import jakarta.annotation.Nullable;
import jakarta.persistence.criteria.Predicate;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

import static java.util.Objects.isNull;
import static org.apache.commons.lang3.StringUtils.isBlank;

public class TrainerTrainingSpecification {

    public static Specification<Training> findByCriteria(@NotNull String username,
                                                         @Nullable LocalDate from,
                                                         @Nullable LocalDate to,
                                                         @Nullable String traineeName,
                                                         @Nullable String trainingType) {
        return Specification.where(trainerPredicate(username))
                .and(dateRangePredicate(from, to))
                .and(traineeNamePredicate(traineeName))
                .and(trainingTypePredicate(trainingType));
    }

    private static Specification<Training> trainerPredicate(String username) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("trainer").get("user").get("username"), username);
    }

    private static Specification<Training> dateRangePredicate(@Nullable LocalDate from, @Nullable LocalDate to) {
        return (root, query, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.conjunction();

            if (!isNull(from)) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.greaterThanOrEqualTo(root.get("trainingDate"), from));
            }
            if (!isNull(to)) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.lessThanOrEqualTo(root.get("trainingDate"), to));
            }

            return predicate;
        };
    }

    private static Specification<Training> traineeNamePredicate(@Nullable String trainerName) {
        return (root, query, criteriaBuilder) -> {
            if (!isBlank(trainerName)) {
                return criteriaBuilder.equal(root.get("trainee").get("user").get("firstName"), trainerName);
            }

            return criteriaBuilder.conjunction();
        };
    }

    private static Specification<Training> trainingTypePredicate(@Nullable String trainingType) {
        return (root, query, criteriaBuilder) -> {
            if (!isBlank(trainingType)) {
                return criteriaBuilder.equal(root.get("trainingType").get("trainingTypeName"), trainingType);
            }

            return criteriaBuilder.conjunction();
        };
    }
}
