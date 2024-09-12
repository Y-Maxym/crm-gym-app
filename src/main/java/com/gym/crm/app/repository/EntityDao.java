package com.gym.crm.app.repository;

import java.util.List;
import java.util.Optional;

public interface EntityDao<ID, T> {

    Optional<T> findById(ID id);

    List<T> findAll();

    T save(T entity);

    T update(T entity);

    void deleteById(ID id);
}
