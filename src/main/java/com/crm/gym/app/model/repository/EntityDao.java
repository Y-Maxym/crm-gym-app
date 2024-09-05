package com.crm.gym.app.model.repository;

import java.util.List;
import java.util.Optional;

public interface EntityDao<ID, T> {

    Optional<T> findById(ID id);

    List<T> findAll();

    void save(T entity);

    void update(T entity);

    void deleteById(ID id);
}
