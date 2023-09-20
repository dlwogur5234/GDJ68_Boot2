package com.winter.app.board.notice;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.winter.app.board.BoardService;
import com.winter.app.board.BoardVO;
import com.winter.app.commons.FileManager;
import com.winter.app.commons.Pager;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j //log기록을 찍고 싶을때 사용하는 어노테이션
public class NoticeService implements BoardService{
	
	@Autowired
	private NoticeDAO noticeDAO;
	@Autowired
	private FileManager fileManager;
	//spEl 스프링에서 사용하는 이엘
	//properties 값을 java에서 사용 하고 싶을때
	//@Value("${properties의 키}")
	@Value("${app.upload}")
	private String uploadPath;
	@Value("${app.board.notice}")
	private String boardName;

	@Override
	public List<BoardVO> getList(Pager pager) throws Exception {
		pager.makeRowNum();
		pager.makePageNum(noticeDAO.getTotal(pager));
		return noticeDAO.getList(pager);
	}

	@Override
	public int add(BoardVO boardVO, MultipartFile [] files) throws Exception {
		// TODO Auto-generated method stub
		log.info("---------------------------------------------------");
		log.info("uploadPath: {}", uploadPath);
		log.info("---------------------------------------------------");

		int result=noticeDAO.add(boardVO, files);
		
		
		
		for(MultipartFile multipartFile:files) {
			String fileName = fileManager.save(this.uploadPath+this.boardName, multipartFile);
					
		}
		return 0;//noticeDAO.add(boardVO, files);
	}

	@Override
	public int setDelete(BoardVO boardVO) throws Exception {
		// TODO Auto-generated method stub
		return noticeDAO.setDelete(boardVO);
				
	}

	@Override
	public int setUpdate(BoardVO boardVO) throws Exception {
		// TODO Auto-generated method stub
		return noticeDAO.setUpdate(boardVO);
	}

	@Override
	public BoardVO getDetail(BoardVO boardVO) throws Exception {
		// TODO Auto-generated method stub
		return noticeDAO.getDetail(boardVO);
	}


}
