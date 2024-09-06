package com.crm.gym.app.model.storage;

import java.util.List;

public interface Storage<K, V> {

    V get(K key);

    List<V> getAll();

    V put(K key, V value);

    void remove(K key);

    void clear();
}
