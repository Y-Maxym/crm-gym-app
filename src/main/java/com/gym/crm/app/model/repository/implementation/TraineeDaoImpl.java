package com.gym.crm.app.model.repository.implementation;

import com.gym.crm.app.model.entity.Trainee;
import com.gym.crm.app.model.repository.EntityDao;
import com.gym.crm.app.model.storage.Storage;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Setter(onMethod_ = @Autowired)
public class TraineeDaoImpl implements EntityDao<Long, Trainee> {

    private Storage<Long, Trainee> storage;

    @Override
    public Optional<Trainee> findById(Long id) {
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public List<Trainee> findAll() {
        return storage.getAll();
    }

    @Override
    public Trainee save(Trainee trainee) {
        return storage.put(trainee);
    }

    @Override
    public Trainee update(Trainee trainee) {
        return storage.put(trainee);
    }

    @Override
    public void deleteById(Long id) {
        storage.remove(id);
    }
}
