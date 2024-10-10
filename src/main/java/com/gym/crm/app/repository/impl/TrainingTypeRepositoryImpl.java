package com.gym.crm.app.repository.impl;

import com.gym.crm.app.entity.TrainingType;
import com.gym.crm.app.repository.TrainingTypeRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class TrainingTypeRepositoryImpl implements TrainingTypeRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<TrainingType> findAll() {
        return entityManager.createQuery("from TrainingType", TrainingType.class)
                .getResultList();
    }

    @Override
    public Optional<TrainingType> findByName(String typeName) {
        try {
            TrainingType trainingType = entityManager
                    .createQuery("FROM TrainingType t " +
                            "WHERE t.trainingTypeName = :typeName", TrainingType.class)
                    .setParameter("typeName", typeName)
                    .getSingleResult();

            return Optional.of(trainingType);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }
}
