package com.crm.gym.app.model.repository;

public interface EntityDao<ID, T> {

    T findById(ID id);

    void save(T entity);

    void update(T entity);

    void deleteById(ID id);
}
