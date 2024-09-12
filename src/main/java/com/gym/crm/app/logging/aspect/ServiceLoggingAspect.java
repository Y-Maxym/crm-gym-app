package com.gym.crm.app.logging.aspect;

import com.gym.crm.app.logging.LogHandler;
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

import static com.gym.crm.app.util.Constants.DEBUG_SERVICE_EXCEPTION;
import static com.gym.crm.app.util.Constants.DEBUG_SERVICE_INPUT;
import static com.gym.crm.app.util.Constants.DEBUG_SERVICE_RESULT;
import static com.gym.crm.app.util.Constants.INFO_SERVICE_EXCEPTION;
import static com.gym.crm.app.util.Constants.INFO_SERVICE_INPUT;
import static com.gym.crm.app.util.Constants.INFO_SERVICE_RESULT;

@Slf4j
@Aspect
@Component
@Setter(onMethod_ = @Autowired)
public class ServiceLoggingAspect {

    private LogHandler logHandler;

    @Pointcut("execution(* com.gym.crm.app.service..*(..))")
    public void serviceMethods() {
    }

    @Before("serviceMethods() && args(*)")
    public void logBefore(JoinPoint joinPoint) {
        logHandler.logBefore(joinPoint, INFO_SERVICE_INPUT, DEBUG_SERVICE_INPUT);
    }

    @AfterReturning(pointcut = "serviceMethods()", returning = "result")
    public void logAfterReturning(JoinPoint joinPoint, Object result) {
        logHandler.logAfterReturning(joinPoint, result, INFO_SERVICE_RESULT, DEBUG_SERVICE_RESULT);
    }

    @AfterThrowing(pointcut = "serviceMethods()", throwing = "ex")
    public void logAfterThrowing(JoinPoint joinPoint, Exception ex) {
        logHandler.logAfterThrowing(joinPoint, ex, INFO_SERVICE_EXCEPTION, DEBUG_SERVICE_EXCEPTION);
    }
}
