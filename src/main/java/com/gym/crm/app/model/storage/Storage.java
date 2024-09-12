package com.gym.crm.app.model.storage;

import com.gym.crm.app.exception.StorageNotFoundException;
import com.gym.crm.app.logging.MessageHelper;
import com.gym.crm.app.model.entity.Trainee;
import com.gym.crm.app.model.entity.Trainer;
import com.gym.crm.app.model.entity.Training;
import com.gym.crm.app.model.entity.User;
import jakarta.annotation.PostConstruct;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static com.gym.crm.app.util.Constants.ERROR_STORAGE_NOT_FOUND;
import static java.util.Objects.isNull;

@Component
@Setter(onMethod_ = @Autowired)
public class Storage {

    private final Map<Class<?>, Map<Long, ?>> globalStorage = new HashMap<>();

    private MessageHelper messageHelper;

    @SuppressWarnings("all")
    public <T> T get(Long id, Class<T> clazz) {
        Map<Long, T> storage = defineStorage(clazz);

        return (T) storage.get(id);
    }

    @SuppressWarnings("all")
    public <T> List<T> getAll(Class<T> clazz) {
        Map<Long, ?> storage = defineStorage(clazz);

        List<T> allEntities = (List<T>) storage.values();

        return Collections.unmodifiableList(allEntities);
    }

    @SuppressWarnings("all")
    public <T> T put(Long id, T entity) {
        Class<T> clazz = (Class<T>) entity.getClass();

        Map<Long, T> storage = defineStorage(clazz);

        storage.put(id, entity);

        return entity;
    }

    public <T> void remove(Long id, Class<T> clazz) {
        Map<Long, T> storage = defineStorage(clazz);

        storage.remove(id);
    }

    public <T> void clear(Class<T> clazz) {
        Map<Long, T> storage = defineStorage(clazz);

        storage.clear();
    }

    @SuppressWarnings("all")
    private <T> Map<Long, T> defineStorage(Class<T> clazz) {
        Map<Long, T> storage = (Map<Long, T>) globalStorage.get(clazz);

        if (isNull(storage)) {
            throw new StorageNotFoundException(messageHelper.getMessage(ERROR_STORAGE_NOT_FOUND, clazz));
        }

        return storage;
    }

    @PostConstruct
    private void init() {
        Map<Long, Trainee> traineeStorage = new ConcurrentHashMap<>();
        Map<Long, Trainer> trainerStorage = new ConcurrentHashMap<>();
        Map<Long, User> userStorage = new ConcurrentHashMap<>();
        Map<Long, Training> trainingStorage = new ConcurrentHashMap<>();

        globalStorage.put(Trainee.class, traineeStorage);
        globalStorage.put(Trainer.class, trainerStorage);
        globalStorage.put(User.class, userStorage);
        globalStorage.put(Training.class, trainingStorage);
    }

}
