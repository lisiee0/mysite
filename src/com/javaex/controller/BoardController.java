package com.javaex.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.javaex.dao.BoardDao;
import com.javaex.util.WebUtil;
import com.javaex.vo.BoardVo;


@WebServlet("/board")
public class BoardController extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String act= request.getParameter("action");
		
		// 게시판 리스트
		if("list".equals(act)) {
			
			BoardDao bd= new BoardDao();
			List<BoardVo> bList= bd.getList();
			
			request.setAttribute("bl", bList);
			
			WebUtil.forward(request, response, "/WEB-INF/views/board/list.jsp");
		}
		
		// 읽기
		else if("read".equals(act)) {
			int no= Integer.parseInt(request.getParameter("no"));
			
			BoardDao bd= new BoardDao();
			BoardVo vo= bd.getPost(no); // 선택한 게시글
			bd.read(no); // 선택한 게시글 조회수+1

			request.setAttribute("po", vo);
			
			WebUtil.forward(request, response, "/WEB-INF/views/board/read.jsp");
		}
		
		// 수정폼
		else if("modifyForm".equals(act)) {
			int no= Integer.parseInt(request.getParameter("no"));
			
			BoardDao bd= new BoardDao();
			BoardVo vo= bd.getPost(no); // 선택한 게시글
			
			request.setAttribute("po", vo);
			
			WebUtil.forward(request, response, "/WEB-INF/views/board/modifyForm.jsp");
		}
		
		// 수정
		else if("modify".equals(act)) {
			int no= Integer.parseInt(request.getParameter("no"));
			String title= request.getParameter("title");
			String content= request.getParameter("content");
			
			BoardDao bd= new BoardDao();
			BoardVo vo= new BoardVo(no, title, content);
			
			bd.modify(vo);
			
			WebUtil.redirect(request, response, "/mysite/board?action=list");
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
