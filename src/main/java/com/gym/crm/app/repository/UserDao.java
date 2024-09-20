package com.gym.crm.app.repository;

import com.gym.crm.app.entity.User;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
@Setter(onMethod_ = @Autowired)
public class UserDao extends CrudDao<Long, User> {

    public UserDao() {
        super(User.class);
    }
}
