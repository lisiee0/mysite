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
	public void userInsert(UserVo vo) {

		getConnection();

		try {
			String query= "";
			query += " insert into users ";
			query += " values(seq_users_no.nextval, ?, ?, ?, ?) ";

			pstmt= conn.prepareStatement(query);

			pstmt.setString(1, vo.getId()); // id
			pstmt.setString(2, vo.getPassword()); // password
			pstmt.setString(3, vo.getName()); // name
			pstmt.setString(4, vo.getGender()); // gender


			int count= pstmt.executeUpdate();	    

			System.out.println("["+count+"건 등록되었습니다.]");

		} catch (SQLException e) {
			System.out.println("error:" + e);
		} 
		close();
	}

	public UserVo getUser(String uid, String pw) {
		UserVo vo= null;

		getConnection();

		try {
			String query= "";
			query += " select   no, "; 
			query += "          id, ";
			query += "          password, ";
			query += "          name, ";
			query += "          gender ";
			query += " from     users ";
			query += " where    id= ? ";
			query += " and      password= ? ";

			pstmt= conn.prepareStatement(query);

			pstmt.setString(1, uid);
			pstmt.setString(2, pw);

			rs= pstmt.executeQuery();

			while(rs.next()) {           
				int no= rs.getInt("no"); 
				String id= rs.getString("id");
				String password= rs.getString("password");
				String name= rs.getString("name");
				String gender= rs.getString("gender");

				vo= new UserVo(no, id, password, name, gender);
			}

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}	
		close();

		return vo;
	}

	// 회원정보수정
	public void userUpdate(UserVo vo) {

		getConnection();

		try {
			String query= "";
			query += " update 	users ";
			query += " set 		password= ?, ";
			query += " 			name= ?, ";
			query += " 			gender= ? ";
			query += " where	no= ? ";

			pstmt= conn.prepareStatement(query);

			pstmt.setString(1, vo.getPassword());
			pstmt.setString(2, vo.getName());
			pstmt.setString(3, vo.getGender());
			pstmt.setInt(4, vo.getNo());

			int count= pstmt.executeUpdate();

			System.out.println("["+count+"건 수정되었습니다.]");

		} catch (SQLException e) {
			System.out.println("error:" + e);
		} 
		close();
	}
}
