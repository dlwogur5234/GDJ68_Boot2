package com.winter.app.commons;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Pager {
	
	//시작 인덱스 번호
	private Long startRow;
	
	//가져올 갯수
	private Long lastRow;
	
	//검색할 컬럼명
	private String kind;
	
	//검색어
	private String search;
	
	private Long page;
	
	//한페이지에 보여질 data(row)의 갯스
	private Long perPage;
	
	//총 페이지의 갯수
	private Long totalPage;
	
	//시작번호
	private Long startNum;
	//끝번호
	private Long lastNum;
	
	//이전블럭활성화
	private boolean pre; //false면 1번블럭, true면 1번 아님
	//다음블럭 활성화
	private boolean next; //false면 마지막블럭, true 마지막 블럭 아님
	
	//null처리 setting
		//startRow null값 제거
		public Long getStartRow() {
			if(this.startRow==null || this.startRow<0) {
				return 0L;
			}
			return this.startRow;
		}
		
		//lastRow null값 제거
		public Long getLastRow() {
			if(this.lastRow==null || this.lastRow<0) {
				return 10L;
			}
			return this.lastRow;
		}
			
		//검색에 null값 제거 
		public String getSearch() {
			if(this.search==null) {
				return "";
			}
			return this.search;
		}
		
		//page null값 제거
		public Long getPage() {
			if(this.page==null) {
				this.page=1L;
			}
			return page;
		}
		
		//perpage null값 제거
		public Long getPerPage() {
			if(this.perPage==null) {
				this.perPage=10L;
			}
			return perPage;
		}	
	
	
	public void makePageNum(Long total) {	
		//1. 전체 갯수로 전체 페이지 수 구하기
		this.totalPage= total/ this.getPerPage();
		if(total%this.getPerPage() != 0) {
			this.totalPage++;
		}
		
		//2. 전체 페이지수로 전체 block 수 구하기
		//한페이지에 출력할 번호의 갯수
		long perBlock=5;
		
		long totalBlock = this.totalPage/perBlock;
		if(this.totalPage%perBlock !=0 ) {
			totalBlock++;
		}
		
		//3. 현재 page번호로 블럭번호 구하기
		//현재 블럭 번호
		long curBlock = this.getPage()/perBlock;
		if(this.getPage()%perBlock != 0) {
			curBlock++;
		}
		
		//4. 현재 블럭번호의 시작번호와 끝번호 구하기
		this.startNum=(curBlock-1)*perBlock+1;
		this.lastNum=curBlock*perBlock;		
		
		//이전 블럭 활성화 여부
		if(curBlock>1) {
			this.pre=true;
		}
		
		//다음 블럭 활성화 여부
		if(curBlock<totalBlock) {
			this.next=true;
		}
		
		//현재 블럭이 마지막 블럭일때 lastNum을 totalPage숫자로 대입
//		if(curBlock==totalBlock) {
		if(!this.next) {
			this.lastNum=totalPage;
		}
	}
	
	public void makeRowNum() {
		this.startRow=(this.getPage()-1)*this.getPerPage()+1;
		this.lastRow=this.page*this.getPerPage();
	}
	
	
	
	
	
	
	
	
	
	
}
