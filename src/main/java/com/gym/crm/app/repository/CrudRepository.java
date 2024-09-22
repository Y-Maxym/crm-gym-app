package com.gym.crm.app.repository;

import java.util.List;
import java.util.Optional;

public interface CrudRepository<T> {

    Optional<T> findById(Long id);

    List<T> findAll();

    T save(T entity);

    T update(T entity);

    void deleteById(Long id);
}
