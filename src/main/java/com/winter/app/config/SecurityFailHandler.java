package com.winter.app.config;

import java.io.IOException;
import java.net.URLEncoder;

import javax.security.auth.login.CredentialExpiredException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SecurityFailHandler implements AuthenticationFailureHandler{

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
		// TODO Auto-generated method stub
		
		log.info("================= Exception : {}", exception);
		String message = "아이디 혹은 비밀번호가 틀렸습니다";
		if(exception instanceof InternalAuthenticationServiceException) {
			//message = "존재하지 않는 ID입니다";
			message= "login.fail.nouser";
		}
		if(exception instanceof BadCredentialsException) {
			//message = "비밀번호가 틀렸습니다";
			message = "login.fail.notpassword";
		}
		if(exception instanceof AccountExpiredException) {
//			isAccountNonExpired() = false
			message = "계정유효기간종료 관리자에게 문의하세요";
		}
		if(exception instanceof LockedException) {
//			isAccountNonLocked = false
			message = "정지된 계정입니다 관리자에게 문의하세요";
		}
		if(exception instanceof CredentialsExpiredException) {
//			isCredentialsNonExpired = false
			message = "비밀번호유효기간 만료입니다 관리자에게 문의하세요";
		}
		if(exception instanceof DisabledException) {
			/* isEnabled = false */
			message="휴면 계정입니다";
		}
		message = URLEncoder.encode(message, "UTF-8");
		response.sendRedirect("/member/login?message="+message);
	}

}
