package com.javaex.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.javaex.vo.UserVo;

public class UserDao {
	
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
	
	
	// 회원가입
	public int userInsert(UserVo vo) {
		int count= 0;
		
		this.getConnection();
		
		try {
			String query= "";
			query += " insert into users ";
			query += " values(seq_users_no.nextval, ?, ?, ?, ?) ";
		
		    pstmt= conn.prepareStatement(query);
		    
		    pstmt.setString(1, vo.getId()); // id
		    pstmt.setString(2, vo.getPassword()); // password
		    pstmt.setString(3, vo.getName()); // name
		    pstmt.setString(4, vo.getGender()); // gender
		    

		    count= pstmt.executeUpdate();	    
		    		   	    
		    System.out.println("["+count+"건 등록되었습니다.]");
		        	    
		} catch (SQLException e) {
		    System.out.println("error:" + e);
		} 
		this.close();
		
		return count;
	}
}
