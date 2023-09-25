package com.winter.app.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class MemberService implements UserDetailsService{

	//DAO
	@Autowired
	private MemberDAO memberDAO;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		log.info("=============로그인시도중============");
		MemberVO memberVO = new MemberVO();
		memberVO.setUsername(username);
		try {
			memberVO = memberDAO.getMember(memberVO);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			memberVO = null;
		}
		return memberVO;
	}
	
	//login
	public MemberVO getLogin(MemberVO memberVO)throws Exception{
		MemberVO loginVO = memberDAO.getMember(memberVO);
		
		if(loginVO == null) {
			return loginVO;
		}
		
		if(loginVO.getPassword().equals(memberVO.getPassword())) {
			return loginVO;
		}
		
		return null;
	}
	
	//검증메서드
	public boolean getMemberError(MemberVO memberVO, BindingResult bindingResult)throws Exception{
		boolean check = false; //false가 error가 없다, true면 검증 실패(error가 있다)
		
		//password 일치 검증
		if(!memberVO.getPassword().equals(memberVO.getPasswordCheck())) {
			check=true; //check=!check
			
			bindingResult.rejectValue("passwordCheck", "memberVO.password.equalCheck");  //앞에꺼가 jsp path, 뒤에꺼가 message의 키
		}
		
		//ID중복검사
		MemberVO checkVO = memberDAO.getMember(memberVO);
		if(checkVO != null) {
			check = true;
			bindingResult.rejectValue("username", "memberVO.username.equalCheck");
		}
		
		return check;
	}

	
	
	
	
	
	
	
	
	
}
