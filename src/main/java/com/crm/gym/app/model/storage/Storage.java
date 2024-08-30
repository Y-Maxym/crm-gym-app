package com.crm.gym.app.model.storage;

public interface Storage<K, V> {

    V get(K key);

    V put(K key, V value);

    void remove(K key);
}
