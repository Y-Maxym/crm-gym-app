package com.crm.gym.app.aspect;

import com.crm.gym.app.util.MessageUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.crm.gym.app.util.Constants.DEBUG_REPOSITORY_EXCEPTION;
import static com.crm.gym.app.util.Constants.DEBUG_REPOSITORY_INPUT;
import static com.crm.gym.app.util.Constants.DEBUG_REPOSITORY_RESULT;
import static com.crm.gym.app.util.Constants.INFO_REPOSITORY_EXCEPTION;
import static com.crm.gym.app.util.Constants.INFO_REPOSITORY_INPUT;
import static com.crm.gym.app.util.Constants.INFO_REPOSITORY_RESULT;

@Slf4j
@Aspect
@Component
public class DaoLoggingAspect extends BaseLoggingAspect {

    @Autowired
    public DaoLoggingAspect(MessageUtils messageUtils) {
        super(messageUtils);
    }

    @Pointcut("execution(* com.crm.gym.app.model.repository.implementation..*(..))")
    public void daoMethods() {
    }

    @Before("daoMethods() && args(*)")
    public void logBefore(JoinPoint joinPoint) {
        logBefore(joinPoint, INFO_REPOSITORY_INPUT, DEBUG_REPOSITORY_INPUT);
    }

    @AfterReturning(pointcut = "daoMethods()", returning = "result")
    public void logAfterReturning(JoinPoint joinPoint, Object result) {
        logAfterReturning(joinPoint, result, INFO_REPOSITORY_RESULT, DEBUG_REPOSITORY_RESULT);
    }

    @AfterThrowing(pointcut = "daoMethods()", throwing = "ex")
    public void logAfterThrowing(JoinPoint joinPoint, Exception ex) {
        logAfterThrowing(joinPoint, ex, INFO_REPOSITORY_EXCEPTION, DEBUG_REPOSITORY_EXCEPTION);
    }
}
