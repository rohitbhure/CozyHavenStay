package com.hexaware.CozyHavenStay.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
//import org.slf4j.Logger;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

//    @Before("execution(* com.hexaware.jpasampleproject.service.ProductServiceImpl.addproduct*.*(..))")
//    public void logMethodCall() {
//        System.out.println("Method called!");
//    }
	@Pointcut("execution(* com.hexaware.CozyHavenStay.service.UserServiceImpl.createUser(..))")
	public void createUserPointcut() {};
	//@Before(pointcut="execution(" com.hexaware.jpasampleproject.service.ProductServiceImpl.addproduct(..))")
	@Before("createUserPointcut()")     //point-cut expression
    public void logBeforeV1(JoinPoint joinPoint) {
		System.out.println("Method Called");
	}
}