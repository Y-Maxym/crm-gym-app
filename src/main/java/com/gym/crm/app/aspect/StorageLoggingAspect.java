package com.gym.crm.app.aspect;

import com.gym.crm.app.util.LoggingUtils;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.gym.crm.app.util.Constants.DEBUG_STORAGE_EXCEPTION;
import static com.gym.crm.app.util.Constants.DEBUG_STORAGE_INPUT;
import static com.gym.crm.app.util.Constants.DEBUG_STORAGE_RESULT;
import static com.gym.crm.app.util.Constants.INFO_STORAGE_EXCEPTION;
import static com.gym.crm.app.util.Constants.INFO_STORAGE_INPUT;
import static com.gym.crm.app.util.Constants.INFO_STORAGE_RESULT;

@Slf4j
@Aspect
@Component
@Setter(onMethod_ = @Autowired)
public class StorageLoggingAspect {

    private LoggingUtils utils;

    @Pointcut("execution(* com.gym.crm.app.model.storage.implementation..*(..))")
    public void storageMethods() {
    }

    @Before("storageMethods() && args(*)")
    public void logBefore(JoinPoint joinPoint) {
        utils.logBefore(joinPoint, INFO_STORAGE_INPUT, DEBUG_STORAGE_INPUT);
    }

    @AfterReturning(pointcut = "storageMethods()", returning = "result")
    public void logAfterReturning(JoinPoint joinPoint, Object result) {
        utils.logAfterReturning(joinPoint, result, INFO_STORAGE_RESULT, DEBUG_STORAGE_RESULT);
    }

    @AfterThrowing(pointcut = "storageMethods()", throwing = "ex")
    public void logAfterThrowing(JoinPoint joinPoint, Exception ex) {
        utils.logAfterThrowing(joinPoint, ex, INFO_STORAGE_EXCEPTION, DEBUG_STORAGE_EXCEPTION);
    }
}
