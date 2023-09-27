package com.winter.app.member;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import com.nimbusds.oauth2.sdk.Role;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class MemberService extends DefaultOAuth2UserService implements UserDetailsService{

	//DAO
	@Autowired
	private MemberDAO memberDAO;
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	
	//social Login
	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		// TODO Auto-generated method stub
		log.info("=========== Social Login 처리 진행===================");
		ClientRegistration clientRegistration = userRequest.getClientRegistration();
//		log.info("====== {} ==========", clientRegistration);
		
		OAuth2User auth2User = super.loadUser(userRequest);
		log.info("====== {} ==========", auth2User);
		
		String social = clientRegistration.getRegistrationId();
		if(social.equals("kakao")) {
			auth2User = this.forKakao(auth2User);
		}
		
		return auth2User;
	}
	
	private OAuth2User forKakao(OAuth2User auth2User){
	      // MemberVO 타입 : UserDetails, OAuth2User
	      MemberVO memberVO = new MemberVO();
	      // memberVO.setUsername(null);
	      
	      
	      /**
	        Key :id            value : 1129858552 -고유 아이디
	      Key :connected_at   value : 2019-07-22T03:05:22Z 
	      Key :properties      value : {nickname=김대기}
	      Key :kakao_account   value : {profile_needs_agreement=false, profile={nickname=김대기, thumbnail_image_url=http://k.kakaocdn.net/dn/dpk9l1/btqmGhA2lKL/Oz0wDuJn1YV2DIn92f6DVK/img_110x110.jpg, profile_image_url=http://k.kakaocdn.net/dn/dpk9l1/btqmGhA2lKL/Oz0wDuJn1YV2DIn92f6DVK/img_640x640.jpg, is_default_image=true}, has_email=true, email_needs_agreement=false, is_email_valid=true, is_email_verified=true, email=kimdaeki@gmail.com}
	       */
	      
	      // auth2User.getAttribute("properties").getClass();  --> class type 확인 [ java.util.LinkedHashMap ] => 리턴타입을 알기 위함.
	      // auth2User.getAttribute("properties");  --> 사용자의 정보 <키 : [nickname, profile_image, thumbnail_image]>
	      LinkedHashMap<String, String> map = auth2User.getAttribute("properties");      
	      log.info("===> auth2User = properties : {} ==>>>>>>", map);

	      LinkedHashMap<String, Object> kakaoAccount = auth2User.getAttribute("kakao_account");
	      LinkedHashMap<String, Object> profile = (LinkedHashMap<String, Object>)kakaoAccount.get("profile");    // Object 타입을 LinkedHashMap으로 변경
	      log.info("===> auth2User = kakao_account : {} ==>>>>>>", profile.get("nickname"));
	      log.info("===> auth2User = kakao_account : {} ==>>>>>>", profile.get("profile_image_url"));
	      log.info("===> auth2User = kakao_account : {} ==>>>>>>", kakaoAccount.get("email"));
	      log.info("===> auth2User = kakao_account : {} ==>>>>>>", kakaoAccount.get("birthday"));
	      
	      
	      String birth = kakaoAccount.get("birthday").toString();//0116
	      String m = birth.substring(0,2);
	      String d = birth.substring(2);
	      Calendar ca = Calendar.getInstance();
	      int y = ca.get(Calendar.YEAR);
	      StringBuffer sb = new StringBuffer();
	      sb.append(y);
	      sb.append("-");
	      sb.append(m);
	      sb.append("-");
	      sb.append(d);
	      
	      memberVO.setUsername(map.get("nickname").toString()); // LinkedHashMap<String, Object> -> LinkedHashMap<String, String> 으로 하면 .toString() 사용 X 
	      memberVO.setName(map.get("nickname").toString());
	      // memberVO.setUsername(profile.get("nickname").toString()); <위 코드와 동일 / map에서 꺼내거나 profile에서 꺼내거나 둥중 하나>
	      memberVO.setEmail(kakaoAccount.get("email").toString());
	      //memberVO.setBirth(kakaoAccount.get("birthday").toString());      // 문자열(birthday) -> 날짜(Date) 변경
	      memberVO.setBirth(Date.valueOf(sb.toString()));
	      
	      memberVO.setAttributes(auth2User.getAttributes());
	      
	      List<RoleVO> list = new ArrayList<>();
	      RoleVO roleVO = new RoleVO();
	      roleVO.setRoleName("ROLE_NUMBER");
	      
	      list.add(roleVO);
	      memberVO.setRoleVOs(list);
	      
	      return memberVO;
	   }
	//Server Login
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

	@Transactional(rollbackFor = Exception.class)
	public int setJoin(MemberVO memberVO) throws Exception{
		memberVO.setPassword(passwordEncoder.encode(memberVO.getPassword()));
		int result = memberDAO.setJoin(memberVO);
		Map<String, Object> map = new HashMap<>();
		map.put("roleNum", 3);
		map.put("username",memberVO.getUsername());
		result = memberDAO.setMemberRole(map);
		
		return result;
	}
	
	
	
	
	
	
	
	
}
