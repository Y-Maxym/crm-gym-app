package com.gym.crm.app.service.impl;

import com.gym.crm.app.entity.Trainee;
import com.gym.crm.app.exception.EntityValidationException;
import com.gym.crm.app.logging.MessageHelper;
import com.gym.crm.app.repository.EntityDao;
import com.gym.crm.app.service.EntityExceptionHelper;
import com.gym.crm.app.service.TraineeService;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.gym.crm.app.util.Constants.ERROR_TRAINEE_WITH_ID_NOT_FOUND;

@Service
@Setter(onMethod_ = @Autowired)
public class TraineeServiceImpl implements TraineeService {

    private MessageHelper messageHelper;
    private EntityDao<Long, Trainee> repository;
    private EntityExceptionHelper exceptionHelper;

    public Trainee findById(Long id) {
        exceptionHelper.checkId(id);

        return repository.findById(id)
                .orElseThrow(() -> new EntityValidationException(messageHelper.getMessage(ERROR_TRAINEE_WITH_ID_NOT_FOUND, id)));
    }

    public void save(Trainee trainee) {
        exceptionHelper.checkEntity(trainee);

        repository.save(trainee);
    }

    public void update(Trainee trainee) {
        exceptionHelper.checkEntity(trainee);
        exceptionHelper.checkId(trainee.getId());

        repository.update(trainee);
    }

    public void deleteById(Long id) {
        exceptionHelper.checkId(id);

        repository.deleteById(id);
    }
}
