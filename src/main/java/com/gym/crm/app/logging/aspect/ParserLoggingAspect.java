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

import static com.gym.crm.app.util.Constants.DEBUG_PARSER_EXCEPTION;
import static com.gym.crm.app.util.Constants.DEBUG_PARSER_INPUT;
import static com.gym.crm.app.util.Constants.DEBUG_PARSER_RESULT;
import static com.gym.crm.app.util.Constants.INFO_PARSER_EXCEPTION;
import static com.gym.crm.app.util.Constants.INFO_PARSER_INPUT;
import static com.gym.crm.app.util.Constants.INFO_PARSER_RESULT;

@Slf4j
@Aspect
@Component
@Setter(onMethod_ = @Autowired)
public class ParserLoggingAspect {

    private LogHandler utils;

    @Pointcut("execution(* com.gym.crm.app.parser..*(..))")
    public void parserMethods() {
    }

    @Before("parserMethods()")
    public void logBefore(JoinPoint joinPoint) {
        utils.logBefore(joinPoint, INFO_PARSER_INPUT, DEBUG_PARSER_INPUT);
    }

    @AfterReturning(pointcut = "parserMethods()", returning = "result")
    public void logAfterReturning(JoinPoint joinPoint, Object result) {
        utils.logAfterReturning(joinPoint, result, INFO_PARSER_RESULT, DEBUG_PARSER_RESULT);
    }

    @AfterThrowing(pointcut = "parserMethods()", throwing = "ex")
    public void logAfterThrowing(JoinPoint joinPoint, Exception ex) {
        utils.logAfterThrowing(joinPoint, ex, INFO_PARSER_EXCEPTION, DEBUG_PARSER_EXCEPTION);
    }
}
