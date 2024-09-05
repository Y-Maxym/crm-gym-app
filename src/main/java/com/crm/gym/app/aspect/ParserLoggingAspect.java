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

import static com.crm.gym.app.util.Constants.PARSING_EXCEPTION;
import static com.crm.gym.app.util.Constants.PARSING_INPUT;
import static com.crm.gym.app.util.Constants.PARSING_RESULT;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class ParserLoggingAspect {

    private final LoggingMessageUtils messageUtils;

    @Pointcut("execution(* com.crm.gym.app.model.parser.implementation..*(..))")
    public void parserMethods() {
    }

    @Before("parserMethods() && args(input)")
    public void logBeforeParse(JoinPoint joinPoint, String input) {
        String className = joinPoint.getSignature().getDeclaringTypeName();
        String methodName = joinPoint.getSignature().getName();

        log.debug(messageUtils.getMessage(PARSING_INPUT, className, methodName, input));
    }

    @AfterReturning(pointcut = "parserMethods()", returning = "result")
    public void logAfterReturning(JoinPoint joinPoint, Object result) {
        String className = joinPoint.getSignature().getDeclaringTypeName();
        String methodName = joinPoint.getSignature().getName();

        log.debug(messageUtils.getMessage(PARSING_RESULT, className, methodName, result));
    }

    @AfterThrowing(pointcut = "parserMethods()", throwing = "ex")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable ex) {
        String methodName = joinPoint.getSignature().getName();
        String message = ex.getMessage();

        log.error(messageUtils.getMessage(PARSING_EXCEPTION, methodName, message));
    }
}
