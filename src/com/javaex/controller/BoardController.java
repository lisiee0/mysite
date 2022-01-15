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
		
		// 읽기
		if("read".equals(act)) {
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
		
		// 게시글 수정
		else if("modify".equals(act)) {
			int no= Integer.parseInt(request.getParameter("no"));
			
			BoardDao bd= new BoardDao();
			BoardVo vo= bd.getPost(no);
			
			// 공백으로 수정하면 기존값으로 업데이트
			String title= request.getParameter("title");
				if("".equals(title)) {
					title= title.replace("", vo.getTitle());
				}
			String content= request.getParameter("content");
				if("".equals(content)) {
					content= content.replace("", vo.getTitle());
				}
				
			vo= new BoardVo(no, title, content);
			
			bd.modify(vo);
			
			WebUtil.redirect(request, response, "/mysite/board");
		}
		
		// 글쓰기폼
		else if("writeForm".equals(act)) {			
			WebUtil.forward(request, response, "/WEB-INF/views/board/writeForm.jsp");
		}
		
		else if("write".equals(act)) {
			int userNo= Integer.parseInt(request.getParameter("userNo"));
			String title= request.getParameter("title");
			String content= request.getParameter("content");
			
			BoardDao bd= new BoardDao();
			bd.write(title, content, userNo);
			
			WebUtil.redirect(request, response, "/mysite/board");	
		}
		
		// 게시글 삭제
		else if("delete".equals(act)) {
			int no= Integer.parseInt(request.getParameter("no"));
			
			BoardDao bd= new BoardDao();
			bd.delete(no);
			
			WebUtil.redirect(request, response, "/mysite/board");
		}
		
		// 게시글 리스트
		else {
			BoardDao bd= new BoardDao();
			List<BoardVo> bList= bd.getList();
			
			request.setAttribute("bl", bList);
			
			WebUtil.forward(request, response, "/WEB-INF/views/board/list.jsp");			
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
