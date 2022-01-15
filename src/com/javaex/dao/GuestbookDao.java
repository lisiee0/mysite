package com.javaex.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.javaex.vo.GuestbookVo;

public class GuestbookDao {
	
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
	
	
	public List<GuestbookVo> getList() {
		List<GuestbookVo> gbList= new ArrayList<GuestbookVo>();
		
		getConnection();
		
		try {
			String query= "";
			query += " select   no, "; 
			query += "          name, ";
			query += "          password, ";
			query += "          content, ";
			query += "          to_char(reg_date, 'YYYY-MM-DD HH:MI:SS') reg_date ";
			query += " from     guestbook ";
			query += " order by reg_date desc ";
			
			pstmt= conn.prepareStatement(query);
			
			rs= pstmt.executeQuery();
		    
			while(rs.next()) {           
				int no= rs.getInt("no"); 
				String name= rs.getString("name");
				String password= rs.getString("password");
				String content= rs.getString("content");
				String regDate= rs.getString("reg_date");
				
				GuestbookVo vo= new GuestbookVo(no, name, password, 
						content.replace(" ", "&nbsp;").replace("\n", "<br>"), regDate);
				gbList.add(vo);
			}

		} catch (SQLException e) {
		    System.out.println("error:" + e);
		} 

		close();
		
		return gbList;
	}
	
	
	public void guestDelete(int no) {
		getConnection();

		try {
			String query= "";
			query += " delete from guestbook ";
			query += " where	   no= ? ";
			
		    pstmt= conn.prepareStatement(query);
		    
		    pstmt.setInt(1, no);
		    
		    int count= pstmt.executeUpdate();
		    
		    System.out.println("["+count+"건 삭제되었습니다.]");
		    
		} catch (SQLException e) {
		    System.out.println("error:" + e);
		}
		close();
	}
	

	public void guestInsert(GuestbookVo vo) {
		
		getConnection();

		try {
			String query= "";
			query += " insert into guestbook ";
			query += " values(seq_guestbook_no.nextval, ?, ?, ?, sysdate) ";
		
		    pstmt= conn.prepareStatement(query);
		    
		    pstmt.setString(1, vo.getName()); // name
		    pstmt.setString(2, vo.getPassword()); // password
		    pstmt.setString(3, vo.getContent()); // content

		    int count= pstmt.executeUpdate();	    
		    		   	    
		    System.out.println("["+count+"건 등록되었습니다.]");
		        	    
		} catch (SQLException e) {
		    System.out.println("error:" + e);
		} 
		close();
	}
	
	
	public GuestbookVo getGuest(int num) {
		GuestbookVo vo= null;
		
		getConnection();
		
		try {
			String query= "";
			query += " select   no, "; 
			query += "          name, ";
			query += "          password, ";
			query += "          content, ";
			query += "          to_char(reg_date, 'YYYY-MM-DD HH:MI:SS') reg_date ";
			query += " from     guestbook ";
			query += " where    no= ? ";

			pstmt= conn.prepareStatement(query);
			
			pstmt.setInt(1, num);
			
			rs= pstmt.executeQuery();
			
			while(rs.next()) {           
            	int no= rs.getInt("no"); 
            	String name= rs.getString("name");
            	String password= rs.getString("password");
            	String content= rs.getString("content");
            	String regDate= rs.getString("reg_date");
            	
            	vo= new GuestbookVo(no, name, password, content, regDate);
            }

		} catch (SQLException e) {
		    System.out.println("error:" + e);
		}	
		close();

		return vo;
	}
}
