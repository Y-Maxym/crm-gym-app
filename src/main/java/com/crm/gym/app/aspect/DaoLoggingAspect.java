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

import static com.crm.gym.app.util.Constants.REPOSITORY_EXCEPTION;
import static com.crm.gym.app.util.Constants.REPOSITORY_INPUT;
import static com.crm.gym.app.util.Constants.REPOSITORY_RESULT;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class DaoLoggingAspect {

    private final LoggingMessageUtils messageUtils;

    @Pointcut("execution(* com.crm.gym.app.model.repository.implementation..*(..))")
    public void daoMethods() {
    }

    @Before("daoMethods() && args(..)")
    public void logBefore(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();

        log.info(messageUtils.getMessage(REPOSITORY_INPUT, methodName, args));
    }

    @AfterReturning(pointcut = "daoMethods()", returning = "result")
    public void logAfterReturning(JoinPoint joinPoint, Object result) {
        String methodName = joinPoint.getSignature().getName();

        log.info(messageUtils.getMessage(REPOSITORY_RESULT, methodName, result));
    }

    @AfterThrowing(pointcut = "daoMethods()", throwing = "ex")
    public void logAfterThrowing(JoinPoint joinPoint, Exception ex) {
        String methodName = joinPoint.getSignature().getName();
        String message = ex.getMessage();

        log.error(messageUtils.getMessage(REPOSITORY_EXCEPTION, methodName, message));
    }
}
