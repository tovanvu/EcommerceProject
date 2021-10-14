package com.vti.LoggingAOP;

import java.util.Objects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 
 * Represents a logging
 *
 *
 * @author PTrXuan Created on Sep 18, 2021
 */
@Aspect
@Component
public class LoggingAdvice {

	Logger log = LoggerFactory.getLogger(LoggingAdvice.class);

	@Pointcut(value = "execution(* com.vti.*.*.*(..))")
	public void executeLogging() {
	}

	@Around("executeLogging() && !@annotation(com.vti.LoggingAOP.NoLogging) && !@target(com.vti.LoggingAOP.NoLogging)")
	public Object applicationLogger(ProceedingJoinPoint joinPoint) throws Throwable {
		Object proceed = joinPoint.proceed();
		String className = "CLASS: [" + joinPoint.getTarget().getClass().getSimpleName() + "],";
		String methodName = " METHOD: [" + joinPoint.getSignature().getName() + "()],";
		log.info("======================= START ======================");
		log.info(className);
		log.info(methodName);
		log.info("REQUEST: ");
		Object[] signatureArgs = joinPoint.getArgs();
		try {
			if (Objects.nonNull(signatureArgs) && signatureArgs.length > 0) {
				for (Object object : signatureArgs) {
					log.info("LIST OF PARAMETERS : " + object);
				}

			} else {
				log.info("[]");
			}
			log.info(className);
			log.info(methodName);
			log.info(" RESPONSE: " + proceed.toString());
		} catch (Exception e) {
			log.info("PARAMETER FAILED, EXCEPTION: {}", e.getMessage());
		}

		return proceed;

	}

	@After("executeLogging()")
	public void doAfter() throws Throwable {
		log.info("=======================  END  ======================");
	}

}
