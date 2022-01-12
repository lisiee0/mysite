package com.javaex.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
		
		// 로그인 폼
		else if("loginForm".equals(act)) {
			System.out.println("user/loginform");
			
			WebUtil.forward(request, response, "/WEB-INF/views/user/loginForm.jsp");
		}
		
		//로그인
		else if("login".equals(act)) {
			System.out.println("user/loginform");
			
			UserDao ud= new UserDao();
			
			String id= request.getParameter("id");
			String password= request.getParameter("password");
			
			UserVo authVo= ud.getUseer(id, password);

			if(authVo==null) {
				System.out.println("로그인실패");
				
				WebUtil.redirect(request, response, "/mysite/user?action=loginForm");
			}
			else {
				System.out.println("로그인성공");
				
				// 세션 생성& authVo 세션에 넣기
				HttpSession session= request.getSession();
				session.setAttribute("authUser", authVo);
				
				WebUtil.redirect(request, response, "/mysite/main");
			}
		}
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doGet(request, response);
	}
}
