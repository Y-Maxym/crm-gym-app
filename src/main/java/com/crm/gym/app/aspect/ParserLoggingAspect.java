package com.crm.gym.app.aspect;

import com.crm.gym.app.util.LoggingMessageUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import static com.crm.gym.app.util.Constants.PARSED_RESULT;
import static com.crm.gym.app.util.Constants.PARSING_INPUT;

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
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getSignature().getDeclaringTypeName();

        log.debug(messageUtils.getMessage(PARSING_INPUT, className, methodName, input));
    }

    @AfterReturning(pointcut = "parserMethods()", returning = "result")
    public void logAfterReturning(JoinPoint joinPoint, Object result) {
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getSignature().getDeclaringTypeName();

        log.debug(messageUtils.getMessage(PARSED_RESULT, className, methodName, result));
    }
}
