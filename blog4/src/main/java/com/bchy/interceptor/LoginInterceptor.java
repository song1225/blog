package com.bchy.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
/**
 * 添加登陆拦截器，检查session是否有值
 * @author liusong
 *
 * @date 2019年11月27日
 */
public class LoginInterceptor extends HandlerInterceptorAdapter{

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		if(request.getSession().getAttribute("user")==null){
			response.sendRedirect("/user/toLoginPage");
			return false;
		}else{
			return true;
		}
	}

}
