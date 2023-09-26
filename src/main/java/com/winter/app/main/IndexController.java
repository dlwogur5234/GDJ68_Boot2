package com.winter.app.main;

import java.util.Enumeration;

import javax.servlet.http.HttpSession;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class IndexController {

	@GetMapping("/")
	public String getIndex(HttpSession session)throws Exception{
		
		SecurityContext context = SecurityContextHolder.getContext();
		
		Authentication authentication = context.getAuthentication();
		
		log.info("=================== GETName : {}==================", authentication.getName());
		
		log.info("=================== Principal : {}==================", authentication.getPrincipal());
		
		log.info("=================== Authorities : {}==================", authentication.getAuthorities());
		
//		Enumeration<String> en = session.getAttributeNames();
//		
//		while(en.hasMoreElements()) {
//			String name = en.nextElement();
//			;
//		}
		
		
		return "index";
	}
}
