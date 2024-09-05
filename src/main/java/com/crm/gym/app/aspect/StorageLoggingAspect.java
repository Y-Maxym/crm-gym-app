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

import static com.crm.gym.app.util.Constants.DEBUG_STORAGE_EXCEPTION;
import static com.crm.gym.app.util.Constants.DEBUG_STORAGE_INPUT;
import static com.crm.gym.app.util.Constants.DEBUG_STORAGE_RESULT;
import static com.crm.gym.app.util.Constants.INFO_STORAGE_EXCEPTION;
import static com.crm.gym.app.util.Constants.INFO_STORAGE_INPUT;
import static com.crm.gym.app.util.Constants.INFO_STORAGE_RESULT;

@Slf4j
@Aspect
@Component
public class StorageLoggingAspect extends BaseLoggingAspect {

    @Autowired
    public StorageLoggingAspect(MessageUtils messageUtils) {
        super(messageUtils);
    }

    @Pointcut("execution(* com.crm.gym.app.model.storage.implementation..*(..))")
    public void storageMethods() {
    }

    @Before("storageMethods() && args(*)")
    public void logBefore(JoinPoint joinPoint) {
        logBefore(joinPoint, INFO_STORAGE_INPUT, DEBUG_STORAGE_INPUT);
    }

    @AfterReturning(pointcut = "storageMethods()", returning = "result")
    public void logAfterReturning(JoinPoint joinPoint, Object result) {
        logAfterReturning(joinPoint, result, INFO_STORAGE_RESULT, DEBUG_STORAGE_RESULT);
    }

    @AfterThrowing(pointcut = "storageMethods()", throwing = "ex")
    public void logAfterThrowing(JoinPoint joinPoint, Exception ex) {
        logAfterThrowing(joinPoint, ex, INFO_STORAGE_EXCEPTION, DEBUG_STORAGE_EXCEPTION);
    }
}
