package com.gym.crm.app.model.repository.implementation;

import com.gym.crm.app.model.entity.Training;
import com.gym.crm.app.model.repository.EntityDao;
import com.gym.crm.app.model.storage.Storage;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static java.util.Objects.isNull;

@Repository
@Setter(onMethod_ = @Autowired)
public class TrainingDaoImpl implements EntityDao<Long, Training> {

    private static long currentId = 1;

    private Storage storage;

    @Override
    public Optional<Training> findById(Long id) {
        Training entity = storage.get(id, Training.class);

        return Optional.ofNullable(entity);
    }

    @Override
    public List<Training> findAll() {
        return storage.getAll(Training.class);
    }

    @Override
    public Training saveOrUpdate(Training training) {
        if (isNull(training.getId())) {
            training.setId(currentId++);
        }

        return storage.put(training.getId(), training);
    }

    @Override
    public void deleteById(Long id) {
        storage.remove(id, Training.class);
    }
}
