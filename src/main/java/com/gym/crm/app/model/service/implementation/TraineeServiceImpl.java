package com.gym.crm.app.model.service.implementation;

import com.gym.crm.app.exception.EntityNotFoundException;
import com.gym.crm.app.model.entity.Trainee;
import com.gym.crm.app.model.repository.EntityDao;
import com.gym.crm.app.model.service.TraineeService;
import com.gym.crm.app.util.MessageUtils;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.gym.crm.app.util.Constants.ERROR_TRAINEE_WITH_ID_NOT_FOUND;

@Service
@Setter(onMethod_ = @Autowired)
public class TraineeServiceImpl implements TraineeService {

    private MessageUtils messageUtils;
    private EntityDao<Long, Trainee> repository;

    public Trainee findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(messageUtils.getMessage(ERROR_TRAINEE_WITH_ID_NOT_FOUND, id)));
    }

    public void save(Trainee trainee) {
        repository.saveOrUpdate(trainee);
    }

    public void update(Trainee trainee) {
        repository.saveOrUpdate(trainee);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
