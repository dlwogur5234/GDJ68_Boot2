package com.winter.app.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

import com.winter.app.interceptors.TestInterceptors;

//spring boot가 실행될때 얘부터 읽어라 configuration
@Configuration    
public class InterceptorConfig implements WebMvcConfigurer{
	
	@Autowired
	private TestInterceptors testInterceptors;
	
	@Autowired
	private LocaleChangeInterceptor localeChangeInterceptor;
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(testInterceptors)
				.addPathPatterns("/notice/list")
				.addPathPatterns("/qna/list")
				
				
				;
		
		registry.addInterceptor(localeChangeInterceptor)
				.addPathPatterns("/**")
				
				;
		
	}
	
	

}
