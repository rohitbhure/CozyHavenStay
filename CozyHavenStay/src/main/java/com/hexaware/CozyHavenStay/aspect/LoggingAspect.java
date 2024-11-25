package com.hexaware.CozyHavenStay.aspect;

import org.apache.logging.log4j.LogManager;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
//import org.slf4j.Logger;

import org.apache.logging.log4j.core.Logger;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {
	Logger log = (Logger) LogManager.getRootLogger();

//    @Before("execution(* com.hexaware.jpasampleproject.service.ProductServiceImpl.addproduct*.*(..))")
//    public void logMethodCall() {
//        System.out.println("Method called!");
//    }
	@Pointcut("execution(* com.hexaware.CozyHavenStay.service.UserServiceImpl.createUser(..))")
	public void createUserPointcut() {};
	//@Before(pointcut="execution(" com.hexaware.jpasampleproject.service.ProductServiceImpl.addproduct(..))")
	/*@Before("createUserPointcut()")     //point-cut expression
    public void logBeforeV1(JoinPoint joinPoint) {
		System.out.println("Method Called");
	}*/

	@Before("createUserPointcut()") // point-cut expression
	public void logBeforeV1(JoinPoint joinPoint) {

		System.out.println("CreateUserAspect.logBeforecreateUser() : " + joinPoint.getSignature().getName());
	}

	@AfterThrowing(pointcut = "execution(* com.hexaware.CozyHavenStay.service.UserServiceImpl.getUserById(..))", throwing = "error")
	public void throwingAdvice(JoinPoint joinPoint, Throwable error) {
		log.info("Method Signature: " + joinPoint.getSignature());
		log.error("ResourceNotFoundException: " + error.getMessage());
		log.error(error.getStackTrace());
	}
}