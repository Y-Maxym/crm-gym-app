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

import static com.crm.gym.app.util.Constants.DEBUG_SERVICE_EXCEPTION;
import static com.crm.gym.app.util.Constants.DEBUG_SERVICE_INPUT;
import static com.crm.gym.app.util.Constants.DEBUG_SERVICE_RESULT;
import static com.crm.gym.app.util.Constants.INFO_SERVICE_EXCEPTION;
import static com.crm.gym.app.util.Constants.INFO_SERVICE_INPUT;
import static com.crm.gym.app.util.Constants.INFO_SERVICE_RESULT;

@Slf4j
@Aspect
@Component
public class ServiceLoggingAspect extends BaseLoggingAspect {

    @Autowired
    public ServiceLoggingAspect(MessageUtils messageUtils) {
        super(messageUtils);
    }

    @Pointcut("execution(* com.crm.gym.app.model.service.implementation..*(..))")
    public void serviceMethods() {
    }

    @Before("serviceMethods() && args(*)")
    public void logBefore(JoinPoint joinPoint) {
        logBefore(joinPoint, INFO_SERVICE_INPUT, DEBUG_SERVICE_INPUT);
    }

    @AfterReturning(pointcut = "serviceMethods()", returning = "result")
    public void logAfterReturning(JoinPoint joinPoint, Object result) {
        logAfterReturning(joinPoint, result, INFO_SERVICE_RESULT, DEBUG_SERVICE_RESULT);
    }

    @AfterThrowing(pointcut = "serviceMethods()", throwing = "ex")
    public void logAfterThrowing(JoinPoint joinPoint, Exception ex) {
        logAfterThrowing(joinPoint, ex, INFO_SERVICE_EXCEPTION, DEBUG_SERVICE_EXCEPTION);
    }
}
