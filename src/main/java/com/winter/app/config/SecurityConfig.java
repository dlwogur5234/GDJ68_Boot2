package com.winter.app.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.winter.app.member.MemberService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	@Autowired
	private SecuritySuccessHandler successHandler;
	@Autowired
	private MemberService memberService;
	
	

	@Bean
	WebSecurityCustomizer webSecurityConfig() {
		//Security에서 무시해야하는 URL 패턴 등록
		return web -> web
			   .ignoring()
			   .antMatchers("/img/**")
			   .antMatchers("/css/**")
			   .antMatchers("/js/**")
			   .antMatchers("/vendor/**")
			   .antMatchers("/resources/**")
			   ;
	}
	
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
		httpSecurity
			.cors()
			.and()
			.csrf()
			.disable()
			.authorizeHttpRequests()
				.antMatchers("/notice/add").hasRole("ADMIN") //DBdata에서 ROLE_ADMIN에서 ROLE_ 제외하고 적음
				.antMatchers("/manager/*").hasAnyRole("ADMIN", "MANAGER")
				.antMatchers("/").permitAll()
				.and()
			//form 관련 설정
			.formLogin()
				.loginPage("/member/login")  //내장된 로그인폼을 사용하지 않고, 개발자가 만든 폼을 사용
				.defaultSuccessUrl("/")
				//.successHandler(successHandler)
				//.failureUrl("/member/login?message=LoginFail")
				.failureHandler(getFailHandler())
				.permitAll()
				.and()
			.logout()
				.logoutUrl("/member/logout")
				//.logoutSuccessUrl("/")
				.addLogoutHandler(getLogoutAdd())
				.logoutSuccessHandler(getLogOutHandler())
				.invalidateHttpSession(true)
				.and()
			.rememberMe() //id기억하기
				.tokenValiditySeconds(60)
				.key("IdRemember")
				.userDetailsService(memberService)
				.authenticationSuccessHandler(successHandler)
				.and()
			.oauth2Login()
			   	.userInfoEndpoint()
			   	.userService(memberService)
			   	.and()
			
			;
		
		return httpSecurity.build();
	}
	
	private SecurityFailHandler getFailHandler() {
		return new SecurityFailHandler();
	}
	
	private SecurityLogoutAdd getLogoutAdd() {
		return new SecurityLogoutAdd();
	}
	
	private securityLogOutHandler getLogOutHandler() {
		return new securityLogOutHandler();
	}
}
