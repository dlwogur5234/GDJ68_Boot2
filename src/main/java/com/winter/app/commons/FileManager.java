package com.winter.app.commons;

import java.io.File;
import java.util.UUID;

import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

@Component
public class FileManager {
	
	//file 저장 후 파일명 리턴
	public String save(String path, MultipartFile multipartFile)throws Exception{
		//어디에? 어떤 파일을?
		//1. 파일 객체 생성
		File file = new File(path);
		
		if(!file.exists()) {
			file.mkdirs();
		}
		
		//2. 저장할 파일명 생성
		String fileName = UUID.randomUUID().toString()+"_"+multipartFile.getOriginalFilename();
		
		//3. 파일을 저장
		file = new File(path, fileName);
		//첫번째 방법 FileCopyUtils.copy
//		FileCopyUtils.copy(multipartFile.getBytes(), file);
		//두번째 방법transferTo
		multipartFile.transferTo(file);
		
		return fileName;
	}
}