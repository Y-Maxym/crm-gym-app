package com.gym.crm.app.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

@RequiredArgsConstructor
public abstract class CrudDao<ID, T> {

    @PersistenceContext
    private EntityManager entityManager;

    private final Class<T> clazz;

    protected T execute(Function<EntityManager, T> action) {
        return action.apply(entityManager);
    }

    protected List<T> executeForList(Function<EntityManager, List<T>> action) {
        return action.apply(entityManager);
    }

    protected void execute(Consumer<EntityManager> action) {
        action.accept(entityManager);
    }

    public Optional<T> findById(ID id) {
        T entity = execute(entityManager -> {
            return entityManager.find(clazz, id);
        });

        return Optional.ofNullable(entity);
    }

    public List<T> findAll() {
        return executeForList(entityManager -> {
            return entityManager.createQuery("FROM " + clazz.getSimpleName(), clazz)
                    .getResultList();
        });
    }

    public T save(T entity) {
        execute(entityManager -> {
            entityManager.persist(entity);
        });

        return entity;
    }

    public void saveAll(T... entities) {
        execute(entityManager -> {
            for (T entity : entities) {
                entityManager.persist(entity);
            }
        });
    }

    public T update(T entity) {
        return execute(entityManager -> {
            return entityManager.merge(entity);
        });
    }

    public void deleteById(ID id) {
        execute(entityManager -> {
            entityManager.createQuery("DELETE FROM " + clazz.getSimpleName() + " t WHERE t.id = :id")
                    .setParameter("id", id)
                    .executeUpdate();
        });
    }

    public void deleteAll() {
        execute(entityManager -> {
            entityManager.createQuery("DELETE FROM " + clazz.getSimpleName())
                    .executeUpdate();
        });
    }
}
