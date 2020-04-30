package com.bchy.interceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
/**
 * 配置需要拦截的url地址，排除去登陆页面以及提交form表单
 * @author liusong
 *
 * @date 2019年11月27日
 */
@Configuration
public class webConfig extends WebMvcConfigurerAdapter{

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new LoginInterceptor())
		.addPathPatterns("/**")
		.excludePathPatterns("/type/**")
		.excludePathPatterns("/tag/**")
		.excludePathPatterns("/archive/**")
		.excludePathPatterns("/about/**")
		.excludePathPatterns("/user/toLoginPage")
		.excludePathPatterns("/user/userLogin")
		.excludePathPatterns("/user/toRegisterPage")
		.excludePathPatterns("/")
		.excludePathPatterns("/admin/toLoginPage")
		.excludePathPatterns("/admin/login");
		super.addInterceptors(registry);
	}
  
}
