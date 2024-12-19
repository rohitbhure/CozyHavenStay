package com.hexaware.CozyHavenStay.aspect;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {
    private static final Logger log = LogManager.getLogger(LoggingAspect.class);

    @Pointcut("execution(* com.hexaware.CozyHavenStay.service.UserServiceImpl.registerUser(..))")
    public void registerUserPointcut() {}

    @Before("registerUserPointcut()")
    public void logBeforeV1(JoinPoint joinPoint) {
        log.info("registerUserAspect.logBeforeregisterUser() : {}", joinPoint.getSignature().getName());
    }

    @AfterThrowing(pointcut = "execution(* com.hexaware.CozyHavenStay.service.UserServiceImpl.findById(..))", throwing = "error")
    public void throwingAdvice(JoinPoint joinPoint, Throwable error) {
        log.info("Method Signature: {}", joinPoint.getSignature());
        log.error("ResourceNotFoundException: {}", error.getMessage(), error);
    }
}
