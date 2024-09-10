package com.gym.crm.app.model.repository;

import java.util.List;
import java.util.Optional;

public interface EntityDao<ID, T> {

    Optional<T> findById(ID id);

    List<T> findAll();

    T saveOrUpdate(T entity);

    void deleteById(ID id);
}
