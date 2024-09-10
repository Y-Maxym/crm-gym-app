package com.gym.crm.app.util;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class LoggingUtilsTest {

    @Mock
    private MessageUtils messageUtils;

    @Mock
    private JoinPoint joinPoint;

    @Mock
    private Signature signature;

    @InjectMocks
    private LoggingUtils loggingUtils;

    private ListAppender<ILoggingEvent> listAppender;

    @BeforeEach
    void setUp() {
        LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
        Logger logger = loggerContext.getLogger(LoggingUtils.class);

        listAppender = new ListAppender<>();
        listAppender.setContext(loggerContext);
        listAppender.start();

        logger.addAppender(listAppender);
        logger.setLevel(Level.TRACE);
    }

    @Test
    @DisplayName("Test log before method")
    void givenJoinPoint_whenLogBefore_thenLogsInfoAndDebugMessages() {
        // given
        String methodName = "testMethod";
        String className = "TestClass";
        Object[] args = {1, "test"};
        String infoMessage = "Info message";
        String debugMessage = "Debug message";

        given(joinPoint.getSignature()).willReturn(signature);
        given(signature.getName()).willReturn(methodName);
        given(signature.getDeclaringTypeName()).willReturn(className);
        given(joinPoint.getArgs()).willReturn(args);
        given(messageUtils.getMessage("INFO_CODE", methodName, className)).willReturn(infoMessage);
        given(messageUtils.getMessage("DEBUG_CODE", className, methodName, Arrays.toString(args))).willReturn(debugMessage);

        // when
        loggingUtils.logBefore(joinPoint, "INFO_CODE", "DEBUG_CODE");

        // then
        assertThat(listAppender.list).extracting("formattedMessage")
                .containsExactly(infoMessage, debugMessage);
    }

    @Test
    @DisplayName("Test log after returning method")
    void givenJoinPointAndResult_whenLogAfterReturning_thenLogsInfoAndDebugMessages() {
        // given
        String methodName = "testMethod";
        String className = "TestClass";
        Object result = "result";
        String infoMessage = "Info message";
        String debugMessage = "Debug message";

        given(joinPoint.getSignature()).willReturn(signature);
        given(signature.getName()).willReturn(methodName);
        given(signature.getDeclaringTypeName()).willReturn(className);
        given(messageUtils.getMessage("INFO_CODE", methodName, className)).willReturn(infoMessage);
        given(messageUtils.getMessage("DEBUG_CODE", className, methodName, result)).willReturn(debugMessage);

        // when
        loggingUtils.logAfterReturning(joinPoint, result, "INFO_CODE", "DEBUG_CODE");

        // then
        assertThat(listAppender.list).extracting("formattedMessage")
                .containsExactly(infoMessage, debugMessage);
    }

    @Test
    @DisplayName("Test log after throwing method")
    void givenJoinPointAndException_whenLogAfterThrowing_thenLogsInfoAndErrorMessages() {
        // given
        String methodName = "testMethod";
        String className = "TestClass";
        Exception ex = new RuntimeException("Error occurred");
        String infoMessage = "Info message";
        String errorMessage = "Error message";

        given(joinPoint.getSignature()).willReturn(signature);
        given(signature.getName()).willReturn(methodName);
        given(signature.getDeclaringTypeName()).willReturn(className);
        given(messageUtils.getMessage("INFO_CODE", methodName, className)).willReturn(infoMessage);
        given(messageUtils.getMessage("ERROR_CODE", className, methodName, ex.getMessage())).willReturn(errorMessage);

        // when
        loggingUtils.logAfterThrowing(joinPoint, ex, "INFO_CODE", "ERROR_CODE");

        // then
        assertThat(listAppender.list).extracting("formattedMessage")
                .containsExactly(infoMessage, errorMessage);
    }
}