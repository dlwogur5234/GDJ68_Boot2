package com.winter.app.board.notice;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.winter.app.board.BoardDAO;

@Repository
@Mapper
public interface NoticeDAO extends BoardDAO{
	//DAO는 더 이상 역할을 하지 않기 때문에 namespace 안적고 interface로 만듦
	//DAO의 풀패키지명과 mapper의 namespace를 알아서 찾아줌, ID는 메서드명과 일치 하는걸로
	//@mapper는 꼭 적어줘야함
	//이제 DAO는 객체를 못만들지만 알아서 만들어줌
	
}
