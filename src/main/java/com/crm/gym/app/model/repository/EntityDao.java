package com.crm.gym.app.model.repository;

import java.util.List;

public interface EntityDao<ID, T> {

    T findById(ID id);

    List<T> findAll();

    void save(T entity);

    void update(T entity);

    void deleteById(ID id);
}
