package com.javaex.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.javaex.dao.GuestbookDao;
import com.javaex.util.WebUtil;
import com.javaex.vo.GuestbookVo;


@WebServlet("/guest")
public class GuestbookController extends HttpServlet {
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String act= request.getParameter("action");
		
		// 방명록 리스트&추가
		if("addList".equals(act)) {
			GuestbookDao gbDao= new GuestbookDao();
			List<GuestbookVo> gbList= gbDao.getList();
			
			request.setAttribute("gl", gbList);
			
			WebUtil.forward(request, response, "/WEB-INF/views/guestbook/addList.jsp");
		}
		
		// 방명록 추가
		else if("add".equals(act)) {
			GuestbookDao gbDao = new GuestbookDao();
			
			String name= request.getParameter("name");
			String password= request.getParameter("password");
			String content= request.getParameter("content");
			
			GuestbookVo gv = new GuestbookVo(name, password, content);
			
			gbDao.guestInsert(gv);
			
			WebUtil.redirect(request, response, "/mysite/guest?action=addList");
		}
		
		// 방명록 삭제폼
		else if("deleteForm".equals(act)) {
			WebUtil.forward(request, response, "/WEB-INF/views/guestbook/deleteForm.jsp");
		}
		
		//방명록 삭제
		else if("delete".equals(act)) {
			GuestbookDao gbDao = new GuestbookDao();
			
			int no= Integer.parseInt(request.getParameter("no"));
			String password1= gbDao.getGuest(no).getPassword();
			String password2= request.getParameter("password");

			
			if(password1.equals(password2)) {
				System.out.println("비밀번호 일치");
				gbDao.guestDelete(no);
			}
			else {
				System.out.println("비밀번호가 불일치");
			}
			
			WebUtil.redirect(request, response, "/mysite/guest?action=addList");
		}
		else { // list를 기본 페이지로 설정
			GuestbookDao gbDao= new GuestbookDao();
			List<GuestbookVo> gbList= gbDao.getList();
			
			request.setAttribute("gl", gbList);
			
			WebUtil.forward(request, response, "/WEB-INF/views/guestbook/addList.jsp");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
