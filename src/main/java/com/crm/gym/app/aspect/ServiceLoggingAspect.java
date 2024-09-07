package com.crm.gym.app.aspect;

import com.crm.gym.app.util.LoggingUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
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
@RequiredArgsConstructor
public class ServiceLoggingAspect {

    private final LoggingUtils utils;

    @Pointcut("execution(* com.crm.gym.app.model.service.implementation..*(..))")
    public void serviceMethods() {
    }

    @Before("serviceMethods() && args(*)")
    public void logBefore(JoinPoint joinPoint) {
        utils.logBefore(joinPoint, INFO_SERVICE_INPUT, DEBUG_SERVICE_INPUT);
    }

    @AfterReturning(pointcut = "serviceMethods()", returning = "result")
    public void logAfterReturning(JoinPoint joinPoint, Object result) {
        utils.logAfterReturning(joinPoint, result, INFO_SERVICE_RESULT, DEBUG_SERVICE_RESULT);
    }

    @AfterThrowing(pointcut = "serviceMethods()", throwing = "ex")
    public void logAfterThrowing(JoinPoint joinPoint, Exception ex) {
        utils.logAfterThrowing(joinPoint, ex, INFO_SERVICE_EXCEPTION, DEBUG_SERVICE_EXCEPTION);
    }
}
