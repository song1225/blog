package com.bchy.handler;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class ExceptionControllerHandler {
	private Logger logger=LoggerFactory.getLogger(ExceptionControllerHandler.class);
	@ExceptionHandler(Exception.class)
	public ModelAndView exceptionHandler(HttpServletRequest request,Exception e){
		ModelAndView mv=new ModelAndView();
		mv.addObject("url",request.getRequestURL());
		mv.addObject("exception", e.getMessage());
		mv.setViewName("error/error");
		logger.error("Request URL :{},Exception:{}",request.getRequestURL(),e);
		return mv;
	}
}
