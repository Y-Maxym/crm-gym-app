package com.crm.gym.app.util;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Slf4j
@Component
@Setter(onMethod_ = @Autowired)
public class LoggingUtils {

    private MessageUtils messageUtils;

    public void logBefore(JoinPoint joinPoint, String infoMessageCode, String debugMessageCode) {
        String className = joinPoint.getSignature().getDeclaringTypeName();
        String methodName = joinPoint.getSignature().getName();

        Object[] args = joinPoint.getArgs();
        String stringArgs = Arrays.toString(args);

        log.info(messageUtils.getMessage(infoMessageCode, methodName, className));
        log.debug(messageUtils.getMessage(debugMessageCode, className, methodName, stringArgs));
    }

    public void logAfterReturning(JoinPoint joinPoint, Object result, String infoMessageCode, String debugMessageCode) {
        String className = joinPoint.getSignature().getDeclaringTypeName();
        String methodName = joinPoint.getSignature().getName();

        log.info(messageUtils.getMessage(infoMessageCode, methodName, className));
        log.debug(messageUtils.getMessage(debugMessageCode, className, methodName, result));
    }

    public void logAfterThrowing(JoinPoint joinPoint, Exception ex, String infoMessageCode, String errorMessageCode) {
        String className = joinPoint.getSignature().getDeclaringTypeName();
        String methodName = joinPoint.getSignature().getName();
        String message = ex.getMessage();

        log.info(messageUtils.getMessage(infoMessageCode, methodName, className));
        log.error(messageUtils.getMessage(errorMessageCode, className, methodName, message));
    }
}
