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

import static com.crm.gym.app.util.LoggingConstants.DEBUG_PARSER_EXCEPTION;
import static com.crm.gym.app.util.LoggingConstants.DEBUG_PARSER_INPUT;
import static com.crm.gym.app.util.LoggingConstants.DEBUG_PARSER_RESULT;
import static com.crm.gym.app.util.LoggingConstants.INFO_PARSER_EXCEPTION;
import static com.crm.gym.app.util.LoggingConstants.INFO_PARSER_INPUT;
import static com.crm.gym.app.util.LoggingConstants.INFO_PARSER_RESULT;

@Slf4j
@Aspect
@Component
public class ParserLoggingAspect extends BaseLoggingAspect {

    @Autowired
    public ParserLoggingAspect(LoggingMessageUtils messageUtils) {
        super(messageUtils);
    }

    @Pointcut("execution(* com.crm.gym.app.model.parser.implementation..*(..))")
    public void parserMethods() {
    }

    @Before("parserMethods()")
    public void logBefore(JoinPoint joinPoint) {
        logBefore(joinPoint, INFO_PARSER_INPUT, DEBUG_PARSER_INPUT);
    }

    @AfterReturning(pointcut = "parserMethods()", returning = "result")
    public void logAfterReturning(JoinPoint joinPoint, Object result) {
        logAfterReturning(joinPoint, result, INFO_PARSER_RESULT, DEBUG_PARSER_RESULT);
    }

    @AfterThrowing(pointcut = "parserMethods()", throwing = "ex")
    public void logAfterThrowing(JoinPoint joinPoint, Exception ex) {
        logAfterThrowing(joinPoint, ex, INFO_PARSER_EXCEPTION, DEBUG_PARSER_EXCEPTION);
    }
}
