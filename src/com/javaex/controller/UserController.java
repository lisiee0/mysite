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
			
			UserVo authVo= ud.getUser(id, password);

			if(authVo==null) {
				System.out.println("로그인실패");
				
				WebUtil.redirect(request, response, "/mysite/user?action=loginForm&result=fail");
			}
			else {
				System.out.println("로그인성공");
				
				// 세션 생성 & authVo 세션에 넣기
				HttpSession session= request.getSession();
				session.setAttribute("authUser", authVo);
				
				WebUtil.redirect(request, response, "/mysite/main");
			}
		}
		
		// 로그아웃
		else if("logout".equals(act)) {
			System.out.println("user/logout");
			
			// 기존 세션이 존재하면 기존 세션 리턴
			HttpSession session= request.getSession();
			session.removeAttribute("authUser"); // 세션에서 객체(authUser) 삭제
			session.invalidate(); // 세션 삭제
			
			WebUtil.redirect(request, response, "/mysite/main");
		}
		
		
		// 회원정보수정 폼
		else if("modifyForm".equals(act)) {
			System.out.println("user/modifyForm");
			
			WebUtil.forward(request, response, "/WEB-INF/views/user/modifyForm.jsp");
		}
		// 회원정보수정
		else if("modify".equals(act)) {
			System.out.println("user/modify");
		
			UserDao ud= new UserDao();
			
			// 기존 세션 불러오기
			HttpSession session= request.getSession();
			UserVo authUser= (UserVo)session.getAttribute("authUser");
			
			int no= Integer.parseInt(request.getParameter("no"));
			String password= request.getParameter("password");
				if("".equals(password)) {
					password= password.replace("", authUser.getPassword());
				}
			String name= request.getParameter("name");
				if("".equals(name)) {
					name= name.replace("", authUser.getName());
				}
			String gender= request.getParameter("gender");
				if(gender==null) {
					gender= authUser.getGender();
				}
			
			// 회원정보 수정 (db)
			UserVo vo= new UserVo(no, password, name, gender);
			ud.userUpdate(vo);
			
			// 세션 authUser 수정
			authUser=ud.getUser(authUser.getId(), password);
			session.setAttribute("authUser", authUser);
			
			WebUtil.redirect(request, response, "/mysite/main");
		}	
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doGet(request, response);
	}
}
