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

import static com.gym.crm.app.util.Constants.DEBUG_PARSE_UTILS_EXCEPTION;
import static com.gym.crm.app.util.Constants.DEBUG_PARSE_UTILS_INPUT;
import static com.gym.crm.app.util.Constants.DEBUG_PARSE_UTILS_RESULT;
import static com.gym.crm.app.util.Constants.INFO_PARSE_UTILS_EXCEPTION;
import static com.gym.crm.app.util.Constants.INFO_PARSE_UTILS_INPUT;
import static com.gym.crm.app.util.Constants.INFO_PARSE_UTILS_RESULT;

@Slf4j
@Aspect
@Component
@Setter(onMethod_ = @Autowired)
public class ParserHelperLoggingAspect {

    private LogHandler logHandler;

    @Pointcut("execution(* com.gym.crm.app.parser.ParserHelper.*(..))")
    public void parserMethods() {
    }

    @Before("parserMethods() && args(*)")
    public void logBefore(JoinPoint joinPoint) {
        logHandler.logBefore(joinPoint, INFO_PARSE_UTILS_INPUT, DEBUG_PARSE_UTILS_INPUT);
    }

    @AfterReturning(pointcut = "parserMethods()", returning = "result")
    public void logAfterReturning(JoinPoint joinPoint, Object result) {
        logHandler.logAfterReturning(joinPoint, result, INFO_PARSE_UTILS_RESULT, DEBUG_PARSE_UTILS_RESULT);
    }

    @AfterThrowing(pointcut = "parserMethods()", throwing = "ex")
    public void logAfterThrowing(JoinPoint joinPoint, Exception ex) {
        logHandler.logAfterThrowing(joinPoint, ex, INFO_PARSE_UTILS_EXCEPTION, DEBUG_PARSE_UTILS_EXCEPTION);
    }
}
