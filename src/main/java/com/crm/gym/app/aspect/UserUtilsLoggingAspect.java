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

import static com.crm.gym.app.util.Constants.USER_UTILS_EXCEPTION;
import static com.crm.gym.app.util.Constants.USER_UTILS_INPUT;
import static com.crm.gym.app.util.Constants.USER_UTILS_RESULT;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class UserUtilsLoggingAspect {

    private final LoggingMessageUtils messageUtils;

    @Pointcut("execution(* com.crm.gym.app.util.UserUtils.*(..))")
    public void userUtilsMethods() {
    }

    @Before("userUtilsMethods() && args(..)")
    public void logBefore(JoinPoint joinPoint) {
        String className = joinPoint.getSignature().getDeclaringTypeName();
        String methodName = joinPoint.getSignature().getName();

        Object[] args = joinPoint.getArgs();
        String stringArgs = Arrays.toString(args);

        log.debug(messageUtils.getMessage(USER_UTILS_INPUT, className, methodName, stringArgs));
    }

    @AfterReturning(pointcut = "userUtilsMethods()", returning = "result")
    public void logAfter(JoinPoint joinPoint, Object result) {
        String className = joinPoint.getSignature().getDeclaringTypeName();
        String methodName = joinPoint.getSignature().getName();

        log.debug(messageUtils.getMessage(USER_UTILS_RESULT, className, methodName, result));
    }

    @AfterThrowing(pointcut = "userUtilsMethods()", throwing = "ex")
    public void logAfterThrowing(JoinPoint joinPoint, Exception ex) {
        String className = joinPoint.getSignature().getDeclaringTypeName();
        String methodName = joinPoint.getSignature().getName();
        String message = ex.getMessage();

        log.error(messageUtils.getMessage(USER_UTILS_EXCEPTION, className, methodName, message));
    }
}
