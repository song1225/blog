package com.bchy.aspect;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LogAspect {
/*	private final Logger logger=LoggerFactory.getLogger(this.getClass());
    @Pointcut("execution(* com.bchy.web.*.*(..))")
    
    
	public void log(){}
    
    @Before("log()")
    public void doBefore(){
    	logger.info("-----------之前---------");
    }
    
    @After("log()")
    public void doAfter(){
    	logger.info("-----------之后---------");
    }*/
}
