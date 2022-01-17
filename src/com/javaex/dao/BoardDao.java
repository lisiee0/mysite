package com.javaex.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.javaex.vo.BoardVo;

public class BoardDao {
	
	private Connection conn = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;
	
	private String driver= "oracle.jdbc.driver.OracleDriver";
	private String url= "jdbc:oracle:thin:@localhost:1521:xe";
	private String id= "webdb";
	private String pw= "webdb";
	
	
	private void getConnection() {
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, id, pw);
			
		} catch (ClassNotFoundException e) {
		    System.out.println("error: 드라이버 로딩 실패 - " + e);
		} catch (SQLException e) {
		    System.out.println("error:" + e);
		}   
	}
	
	
	private void close() {
	    try {
	        if (rs != null) {
	            rs.close();
	        }                
	        if (pstmt != null) {
	            pstmt.close();
	        }
	        if (conn != null) {
	            conn.close();
	        }
	    } catch (SQLException e) {
	        System.out.println("error:" + e);
	    }
	}
	
	// 리스트에 넣기
	public List<BoardVo> getList() {
		List<BoardVo> bList= new ArrayList<BoardVo>();
		
		getConnection();
		
		try {
			String query= "";
			query += " select   b.no no, "; 
			query += "          title, ";
			query += "          content, ";
			query += "          hit, ";
			query += "          to_char(reg_date, 'YYYY-MM-DD HH:MI:SS') regDate, ";
			query += "          user_no, ";
			query += "          u.name username";
			query += " from     users u, board b ";
			query += " where    u.no= b.user_no ";
			query += " order by reg_date desc ";
			
			pstmt= conn.prepareStatement(query);
			
			rs= pstmt.executeQuery();
		    
			while(rs.next()) {           
				int no= rs.getInt("no"); 
				String title= rs.getString("title");
				String content= rs.getString("content");
				int hit= rs.getInt("hit");
				String regDate= rs.getString("regDate");
				String userName= rs.getString("username");
				int userNo= rs.getInt("user_no");
				
				BoardVo vo= new BoardVo(no, title, content, hit, regDate, userName, userNo);
				bList.add(vo);
			}

		} catch (SQLException e) {
		    System.out.println("error:" + e);
		} 

		close();
		
		return bList;
	}
	
	// 조회수 +1
	public void read(int bno) {

		getConnection();
		
		try {
			String query= "";
			query += " update   board "; 
			query += " set      hit= hit+1 ";
			query += " where    no= ? ";

			pstmt= conn.prepareStatement(query);

			pstmt.setInt(1, bno);
			
			int count= pstmt.executeUpdate();

			System.out.println("["+bno+"번 게시글 조회수 "+count+"증가했습니다.]");
			
		} catch (SQLException e) {
		    System.out.println("error:" + e);
		}	
		close();
	}
	
	// 특정 게시글 선택 
	public BoardVo getPost(int bno) {
		BoardVo vo= null;
		
		getConnection();
		
		try {
			String query = "";
			query += " select   b.no, "; 
			query += "          title, ";
			query += "          content, ";
			query += "          hit, ";
			query += "          to_char(reg_date, 'YYYY-MM-DD HH:MI:SS') regDate, ";
			query += "          user_no, ";
			query += "          u.name username ";
			query += " from     users u, board b ";
			query += " where    u.no= b.user_no ";
			query += " and		b.no= ? ";
			
			pstmt = conn.prepareStatement(query);
			
			pstmt.setInt(1, bno);
			
			rs = pstmt.executeQuery();

			while(rs.next()) {
				int no= rs.getInt("no"); 
				String title= rs.getString("title");
				String content= rs.getString("content");
				int hit= rs.getInt("hit");
				String regDate= rs.getString("regDate");
				String userName= rs.getString("username");
				int userNo= rs.getInt("user_no");
				
				vo= new BoardVo(no, title, content.replace(" ", "&nbsp;").replace("\n", "<br>")
						, hit, regDate, userName, userNo);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		close();
		
		return vo;
	}
	
	// 게시글 수정
	public void modify(BoardVo vo) {

		getConnection();
		
		try {
			String query= "";
			query += " update   board "; 
			query += " set      title= ?, ";
			query += "          content= ? ";
			query += " where    no= ? ";

			pstmt= conn.prepareStatement(query);
			
			pstmt.setString(1, vo.getTitle());
			pstmt.setString(2, vo.getContent());
			pstmt.setInt(3, vo.getNo());
			
			int count= pstmt.executeUpdate();

			System.out.println("["+vo.getNo()+"번 게시글이 "+count+"번 수정되었습니다.]");
			
		} catch (SQLException e) {
		    System.out.println("error:" + e);
		}	
		close();
	}
	
	// 게시글 작성
	public void write(String title, String content, int userNo) {
		
		getConnection();
	
		try {
			String query= "";
			query += " insert into board ";
			query += " values(seq_board_no.nextval, ?, ?, 0, sysdate, ?) ";
		
		    pstmt= conn.prepareStatement(query);
		    
		    pstmt.setString(1, title); // title
		    pstmt.setString(2, content); // content
		    pstmt.setInt(3, userNo); // userNo
	
		    int count= pstmt.executeUpdate();	    
		    		   	    
		    System.out.println("["+count+"건 등록되었습니다.]");
		        	    
		} catch (SQLException e) {
		    System.out.println("error:" + e);
		} 
		close();
	}
	
	// 게시글 삭제
	public void delete(int bno) {
		
		getConnection();
	
		try {
			String query= "";
			query += " delete from board ";
			query += " where	   no= ? ";
			
		    pstmt= conn.prepareStatement(query);
		    
		    pstmt.setInt(1, bno);
		    
		    int count= pstmt.executeUpdate();
		    
		    System.out.println("["+count+"건 삭제되었습니다.]");
		    
		} catch (SQLException e) {
		    System.out.println("error:" + e);
		}
		close();
	}
}

