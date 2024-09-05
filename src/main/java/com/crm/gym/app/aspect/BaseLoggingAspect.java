package com.crm.gym.app.aspect;

import com.crm.gym.app.util.LoggingMessageUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;

import java.util.Arrays;

@Slf4j
@RequiredArgsConstructor
public abstract class BaseLoggingAspect {

    private final LoggingMessageUtils messageUtils;

    protected void logBefore(JoinPoint joinPoint, String infoMessageCode, String debugMessageCode) {
        String className = joinPoint.getSignature().getDeclaringTypeName();
        String methodName = joinPoint.getSignature().getName();

        Object[] args = joinPoint.getArgs();
        String stringArgs = Arrays.toString(args);

        log.info(messageUtils.getMessage(infoMessageCode, methodName, className));
        log.debug(messageUtils.getMessage(debugMessageCode, className, methodName, stringArgs));
    }

    protected void logAfterReturning(JoinPoint joinPoint, Object result, String infoMessageCode, String debugMessageCode) {
        String className = joinPoint.getSignature().getDeclaringTypeName();
        String methodName = joinPoint.getSignature().getName();

        log.info(messageUtils.getMessage(infoMessageCode, methodName, className));
        log.debug(messageUtils.getMessage(debugMessageCode, className, methodName, result));
    }

    protected void logAfterThrowing(JoinPoint joinPoint, Exception ex, String infoMessageCode, String errorMessageCode) {
        String className = joinPoint.getSignature().getDeclaringTypeName();
        String methodName = joinPoint.getSignature().getName();
        String message = ex.getMessage();

        log.info(messageUtils.getMessage(infoMessageCode, methodName, className));
        log.error(messageUtils.getMessage(errorMessageCode, className, methodName, message));
    }
}
