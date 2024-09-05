package com.crm.gym.app.aspect;

import com.crm.gym.app.util.LoggingMessageUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.crm.gym.app.util.LoggingConstants.DEBUG_PARSE_UTILS_EXCEPTION;
import static com.crm.gym.app.util.LoggingConstants.DEBUG_PARSE_UTILS_INPUT;
import static com.crm.gym.app.util.LoggingConstants.DEBUG_PARSE_UTILS_RESULT;
import static com.crm.gym.app.util.LoggingConstants.INFO_PARSE_UTILS_EXCEPTION;
import static com.crm.gym.app.util.LoggingConstants.INFO_PARSE_UTILS_INPUT;
import static com.crm.gym.app.util.LoggingConstants.INFO_PARSE_UTILS_RESULT;

@Slf4j
@Aspect
@Component
public class ParserUtilsLoggingAspect extends BaseLoggingAspect {

    @Autowired
    public ParserUtilsLoggingAspect(LoggingMessageUtils messageUtils) {
        super(messageUtils);
    }

    @Pointcut("execution(* com.crm.gym.app.util.ParseUtils.*(..))")
    public void parserMethods() {
    }

    @Before("parserMethods() && args(*)")
    public void logBefore(JoinPoint joinPoint) {
        logBefore(joinPoint, INFO_PARSE_UTILS_INPUT, DEBUG_PARSE_UTILS_INPUT);
    }

    @AfterReturning(pointcut = "parserMethods()", returning = "result")
    public void logAfterReturning(JoinPoint joinPoint, Object result) {
        logAfterReturning(joinPoint, result, INFO_PARSE_UTILS_RESULT, DEBUG_PARSE_UTILS_RESULT);
    }

    @AfterThrowing(pointcut = "parserMethods()", throwing = "ex")
    public void logAfterThrowing(JoinPoint joinPoint, Exception ex) {
        logAfterThrowing(joinPoint, ex, INFO_PARSE_UTILS_EXCEPTION, DEBUG_PARSE_UTILS_EXCEPTION);
    }
}
