package com.gym.crm.app.repository.impl;

import com.gym.crm.app.repository.UserRepository;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
@Setter(onMethod_ = @Autowired)
public class UserRepositoryImpl extends UserRepository {
}
