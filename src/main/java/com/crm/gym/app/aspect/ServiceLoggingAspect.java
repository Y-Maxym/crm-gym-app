package com.crm.gym.app.aspect;

import com.crm.gym.app.util.LoggingMessageUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.Arrays;

import static com.crm.gym.app.util.Constants.SERVICE_EXCEPTION;
import static com.crm.gym.app.util.Constants.SERVICE_INPUT;
import static com.crm.gym.app.util.Constants.SERVICE_RESULT;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class ServiceLoggingAspect {

    private final LoggingMessageUtils messageUtils;

    @Pointcut("execution(* com.crm.gym.app.model.service.implementation..*(..))")
    public void serviceMethods() {
    }

    @Before("serviceMethods() && args(..)")
    public void logBefore(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();
        String arguments = Arrays.toString(args);

        log.info(messageUtils.getMessage(SERVICE_INPUT, methodName, arguments));
    }

    @AfterReturning(pointcut = "serviceMethods()", returning = "result")
    public void logAfterReturning(JoinPoint joinPoint, Object result) {
        String methodName = joinPoint.getSignature().getName();

        log.info(messageUtils.getMessage(SERVICE_RESULT, methodName, result));
    }

    @AfterThrowing(pointcut = "serviceMethods()", throwing = "exception")
    public void logAfterThrowing(JoinPoint joinPoint, Exception exception) {
        String methodName = joinPoint.getSignature().getName();
        String message = exception.getMessage();

        log.error(messageUtils.getMessage(SERVICE_EXCEPTION, methodName, message));
    }
}
