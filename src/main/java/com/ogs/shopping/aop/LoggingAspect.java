package com.ogs.shopping.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class LoggingAspect {

//    @Before("execution(* com.ogs.shopping.service.impl.ProductServiceImpl.addProduct(..))")
//    public void logBeforeAddProduct(JoinPoint joinPoint) {
//           System.out.println("Aspect before add product");
//    }

@Around("execution(* com.ogs.shopping.service.impl.ProductServiceImpl.addProduct(..))")
public Object logBeforeAddProduct(ProceedingJoinPoint joinPoint) throws Throwable {
    System.out.println("Aspect before add product");
    Object returnValue = joinPoint.proceed();
    System.out.println("Aspect after add product");
    return returnValue;
}
}
