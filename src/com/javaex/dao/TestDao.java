package com.javaex.dao;

import com.javaex.vo.UserVo;

public class TestDao {

	public static void main(String[] args) {
		UserVo vo= new UserVo("ccc", "1111", "강호동", "male");
		
		UserDao ud= new UserDao();
		
		
		ud.userInsert(vo);
		

	}

}
