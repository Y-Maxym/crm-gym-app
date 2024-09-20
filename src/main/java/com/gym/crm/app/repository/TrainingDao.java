package com.gym.crm.app.repository;

import com.gym.crm.app.entity.Training;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
@Setter(onMethod_ = @Autowired)
public class TrainingDao extends CrudDao<Long, Training> {

    public TrainingDao() {
        super(Training.class);
    }
}
