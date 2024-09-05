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

import static com.crm.gym.app.util.Constants.PARSE_UTILS_EXCEPTION;
import static com.crm.gym.app.util.Constants.PARSE_UTILS_INPUT;
import static com.crm.gym.app.util.Constants.PARSE_UTILS_RESULT;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class ParserUtilsLoggingAspect {

    private final LoggingMessageUtils messageUtils;

    @Pointcut("execution(* com.crm.gym.app.util.ParseUtils.*(..))")
    public void parserMethods() {
    }

    @Before("parserMethods() && args(input)")
    public void logBefore(JoinPoint joinPoint, Object input) {
        String className = joinPoint.getSignature().getDeclaringTypeName();
        String methodName = joinPoint.getSignature().getName();

        log.debug(messageUtils.getMessage(PARSE_UTILS_INPUT, className, methodName, input));
    }

    @AfterReturning(pointcut = "parserMethods()", returning = "result")
    public void logAfter(JoinPoint joinPoint, Object result) {
        String className = joinPoint.getSignature().getDeclaringTypeName();
        String methodName = joinPoint.getSignature().getName();

        log.debug(messageUtils.getMessage(PARSE_UTILS_RESULT, className, methodName, result));
    }

    @AfterThrowing(pointcut = "parserMethods()", throwing = "ex")
    public void logAfterThrowing(JoinPoint joinPoint, Exception ex) {
        String className = joinPoint.getSignature().getDeclaringTypeName();
        String methodName = joinPoint.getSignature().getName();
        String message = ex.getMessage();

        log.error(messageUtils.getMessage(PARSE_UTILS_EXCEPTION, className, methodName, message));
    }
}
