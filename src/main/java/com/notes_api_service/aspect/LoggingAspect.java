package com.notes_api_service.aspect;


import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class LoggingAspect {

    //Controller layer
    @Around("execution(* com.notes_api_service.controller..*(..))")
    public Object trackExecutionTimeForControllers(ProceedingJoinPoint joinPoint) throws Throwable {
        Signature signature = joinPoint.getSignature();
        String className = signature.getDeclaringType().getSimpleName();
        String methodName = signature.getName();
        log.info("Calling :: {}  :: {}()", className, methodName);

        long start = System.currentTimeMillis();
        Object result= joinPoint.proceed();
        long end = System.currentTimeMillis();

        //log.info("Execution time :: {}():: {} ms", joinPoint.getSignature().getName(),(end - start) );
        log.info("End calling :: {}  :: {}() :: Execution time- {} ms", className, methodName,(end - start));
        return result;
    }


    @AfterReturning("execution(* com.notes_api_service.controller..*(..))")
    public void handleAfterReturningFromControllerMethods(JoinPoint joinPoint) {
        Signature signature = joinPoint.getSignature();
        String className = signature.getDeclaringType().getSimpleName();
        String methodName = signature.getName();
        log.info("Return Successfully :: {}  :: {}()", className, methodName);
    }

    @AfterThrowing("execution(* com.notes_api_service.controller..*(..))")
    public void handleAfterThrowingFromControllerMethods(JoinPoint joinPoint) {
        Signature signature = joinPoint.getSignature();
        String className = signature.getDeclaringType().getSimpleName();
        String methodName = signature.getName();
        log.info("Throw An exception :: {}  :: {}()", className, methodName);
    }
    //service layer
    @Around("execution(* com.notes_api_service.service..*(..))")
    public Object trackExecutionTimeForService(ProceedingJoinPoint joinPoint) throws Throwable {
        Signature signature = joinPoint.getSignature();
        String className = signature.getDeclaringType().getSimpleName();
        String methodName = signature.getName();
        log.info("Calling :: {}  :: {}()", className, methodName);

        long start = System.currentTimeMillis();
        Object result= joinPoint.proceed();
        long end = System.currentTimeMillis();

        //log.info("Execution time :: {}():: {} ms", joinPoint.getSignature().getName(),(end - start) );
        log.info("End calling :: {}  :: {}() :: Execution time- {} ms", className, methodName,(end - start));
        return result;
    }




}
