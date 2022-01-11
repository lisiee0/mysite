package com.javaex.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.javaex.dao.UserDao;
import com.javaex.util.WebUtil;
import com.javaex.vo.UserVo;


@WebServlet("/user")
public class UserController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("/user");
		
		String act= request.getParameter("action");
		
		// 회원가입 폼
		if ("joinForm".equals(act)) {
			System.out.println("user/joinForm");
			WebUtil.forward(request, response, "/WEB-INF/views/user/joinForm.jsp");
		}
		
		// 회원가입(insert)
		else if("join".equals(act)) {
			System.out.println("user/join");
			
			UserDao ud= new UserDao();
			
			String id= request.getParameter("id");
			String password= request.getParameter("password");
			String name= request.getParameter("name");
			String gender= request.getParameter("gender");
			
			UserVo vo= new UserVo(id, password, name, gender);
			ud.userInsert(vo);
			
			WebUtil.forward(request, response, "/WEB-INF/views/user/joinOk.jsp");
		}
		else if("loginForm".equals(act)) {
			System.out.println("user/loginform");
			
			WebUtil.forward(request, response, "/WEB-INF/views/user/loginForm.jsp");
		}
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doGet(request, response);
	}
}
