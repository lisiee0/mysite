package com.javaex.dao;

import java.util.List;

import com.javaex.vo.BoardVo;

public class TestDao {

	public static void main(String[] args) {
		
		/*
		UserVo vo= new UserVo("ccc", "1111", "강호동", "male");
		
		UserDao ud= new UserDao();
		
		
		ud.userInsert(vo);
		*/

		BoardDao bd= new BoardDao();
		
		// 리스트 불러오기 
		List<BoardVo> vo= bd.getList();
		for(BoardVo bv: vo) {
			System.out.println(bv.toString());
		}
		
		
	}
}
