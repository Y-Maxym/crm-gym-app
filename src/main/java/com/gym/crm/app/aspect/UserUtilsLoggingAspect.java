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

import static com.gym.crm.app.util.Constants.DEBUG_USER_UTILS_EXCEPTION;
import static com.gym.crm.app.util.Constants.DEBUG_USER_UTILS_INPUT;
import static com.gym.crm.app.util.Constants.DEBUG_USER_UTILS_RESULT;
import static com.gym.crm.app.util.Constants.INFO_USER_UTILS_EXCEPTION;
import static com.gym.crm.app.util.Constants.INFO_USER_UTILS_INPUT;
import static com.gym.crm.app.util.Constants.INFO_USER_UTILS_RESULT;

@Slf4j
@Aspect
@Component
@Setter(onMethod_ = @Autowired)
public class UserUtilsLoggingAspect {

    private LoggingUtils utils;

    @Pointcut("execution(* com.gym.crm.app.util.UserUtils.*(..))")
    public void userUtilsMethods() {
    }

    @Before("userUtilsMethods() && args(..)")
    public void logBefore(JoinPoint joinPoint) {
        utils.logBefore(joinPoint, INFO_USER_UTILS_INPUT, DEBUG_USER_UTILS_INPUT);
    }

    @AfterReturning(pointcut = "userUtilsMethods()", returning = "result")
    public void logAfterReturning(JoinPoint joinPoint, Object result) {
        utils.logAfterReturning(joinPoint, result, INFO_USER_UTILS_RESULT, DEBUG_USER_UTILS_RESULT);
    }

    @AfterThrowing(pointcut = "userUtilsMethods()", throwing = "ex")
    public void logAfterThrowing(JoinPoint joinPoint, Exception ex) {
        utils.logAfterThrowing(joinPoint, ex, INFO_USER_UTILS_EXCEPTION, DEBUG_USER_UTILS_EXCEPTION);
    }
}
