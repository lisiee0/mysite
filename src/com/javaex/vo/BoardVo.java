package com.javaex.vo;

public class BoardVo {
	
	private int no;
	private String title;
	private String content;
	private int count;
	private String regDate;
	private int userNo;
	
	public BoardVo() {
		
	}
	
	public BoardVo(int no, String title, String content, int count, String regDate, int userNo) {
		this.no = no;
		this.title = title;
		this.content = content;
		this.count = count;
		this.regDate = regDate;
		this.userNo = userNo;
	}

	
	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public String getRegDate() {
		return regDate;
	}

	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}

	public int getUserNo() {
		return userNo;
	}

	public void setUserNo(int userNo) {
		this.userNo = userNo;
	}

	@Override
	public String toString() {
		return "BoardVo [no=" + no + ", title=" + title + ", content=" + content + ", count=" + count + ", regDate="
				+ regDate + ", userNo=" + userNo + "]";
	}
}
