package com.crm.gym.app.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class ParserUtilsLoggingAspect {

    @Pointcut("execution(* com.crm.gym.app.util.ParseUtils.*(..))")
    public void parserMethods() {
    }

    @AfterThrowing(pointcut = "parserMethods()", throwing = "ex")
    public void logAfterThrowing(Exception ex) {
        String message = ex.getMessage();

        log.error(message);
    }
}
