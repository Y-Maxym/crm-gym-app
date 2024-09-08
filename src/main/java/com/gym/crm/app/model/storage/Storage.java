package com.gym.crm.app.model.storage;

import java.util.List;

public interface Storage<ID, E> {

    E get(ID id);

    List<E> getAll();

    E put(E entity);

    void remove(ID id);

    void clear();
}
