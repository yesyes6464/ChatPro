package user;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class UserDAO {
	
	DataSource dataSource;
	
	public UserDAO() {
		try {
			InitialContext initContext = new InitialContext();
			Context envContext = (Context)initContext.lookup("java:/comp/env");
			dataSource = (DataSource)envContext.lookup("jdbc/jspbeginner");
		} catch (Exception e) {
			System.out.println("UserDAO오류"+e);
		}
		
	}
	
	public int login(String userID, String userPASS) {
		Connection conn = null;
		PreparedStatement pstmt =  null;
		ResultSet rs = null;
		String SQL = "SELECT * from userchat WHERE userID = ?"; 
		try {
			conn=dataSource.getConnection();
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, userID);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				if(rs.getString("userPASS").equals(userPASS)) {
					return 1;
				}
				return 2;
			}else {
				return 0;
			}
		} catch (Exception e) {
			System.out.println("login()메소드 오류"+e);
		} finally {
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(conn !=null) conn.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return -1; //데이터베이스 오류 
	}
	
	public int registerCheck(String userID) {
		Connection conn = null;
		PreparedStatement pstmt =  null;
		ResultSet rs = null;
		String SQL = "SELECT * from userchat WHERE userID = ?"; 
		try {
			conn=dataSource.getConnection();
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, userID);
			rs = pstmt.executeQuery();
			
			if(rs.next() || userID.equals("")) {
				return 0; //이미 존재하는 회원
			}else {
				return 1; //가입 가능한 회원 아이디 
			}
		} catch (Exception e) {
			System.out.println("login()메소드 오류"+e);
		} finally {
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(conn !=null) conn.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return -1; //데이터베이스 오류 
	}
	
	public int register(String userID, String userPASS, String userName,String userAge, String userGender, String userEmail, String userProfile) {
		Connection conn = null;
		PreparedStatement pstmt =  null;
		String SQL = "INSERT INTO userchat VALUES (?, ?, ?, ?, ?, ?, ?)"; 
		try {
			conn=dataSource.getConnection();
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, userID);
			pstmt.setString(2, userPASS);
			pstmt.setString(3, userName);
			pstmt.setInt(4, Integer.parseInt(userAge));
			pstmt.setString(5, userGender);
			pstmt.setString(6, userEmail);
			pstmt.setString(7, userProfile);
			return pstmt.executeUpdate();
			
		} catch (Exception e) {
			System.out.println("login()메소드 오류"+e);
		} finally {
			try {
				if(pstmt != null) pstmt.close();
				if(conn !=null) conn.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return -1; //데이터베이스 오류 
	}
	
	public userDTO getUser(String userID) {
		userDTO user = new userDTO();
		Connection conn = null;
		PreparedStatement pstmt =  null;
		ResultSet rs = null;
		String SQL = "SELECT * from userchat WHERE userID = ?"; 
		try {
			conn=dataSource.getConnection();
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, userID);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				user.setUserID(userID);
				user.setUserPASS(rs.getString("userPASS"));
				user.setUserName(rs.getString("userName"));
				user.setUserAge(rs.getInt("userAge"));
				user.setUserGender(rs.getString("userGender"));
				user.setUserEmail(rs.getString("userEmail"));
				user.setUserProfile(rs.getString("userProfile"));
			}
		} catch (Exception e) {
			System.out.println("getUser()메소드 오류"+e);
		} finally {
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(conn !=null) conn.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return user; //데이터베이스 오류 
	}
	
	public int update(String userID, String userPASS, String userName,String userAge, String userGender, String userEmail) {
		Connection conn = null;
		PreparedStatement pstmt =  null;
		String SQL = "UPDATE userchat set userPASS = ?, userName = ?, userAge = ?, userGender = ?, userEmail = ? WHERE userID = ?"; 
		try {
			conn=dataSource.getConnection();
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, userPASS);
			pstmt.setString(2, userName);
			pstmt.setInt(3, Integer.parseInt(userAge));
			pstmt.setString(4, userGender);
			pstmt.setString(5, userEmail);
			pstmt.setString(6, userID);
			return pstmt.executeUpdate();
			
		} catch (Exception e) {
			System.out.println("update()메소드 오류"+e);
		} finally {
			try {
				if(pstmt != null) pstmt.close();
				if(conn !=null) conn.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return -1; //데이터베이스 오류 
	}
	
	public int profile(String userID, String userProfile) {
		Connection conn = null;
		PreparedStatement pstmt =  null;
		String SQL = "UPDATE userchat set userProfile = ? WHERE userID = ?"; 
		try {
			conn=dataSource.getConnection();
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, userProfile);
			pstmt.setString(2, userID);
			return pstmt.executeUpdate();
			
		} catch (Exception e) {
			System.out.println("update()메소드 오류"+e);
		} finally {
			try {
				if(pstmt != null) pstmt.close();
				if(conn !=null) conn.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return -1; //데이터베이스 오류 
	}
	
	public String getProfile(String userID) {
		Connection conn = null;
		PreparedStatement pstmt =  null;
		ResultSet rs = null;
		String SQL = "SELECT userProfile from userchat WHERE userID = ?"; 
		try {
			conn=dataSource.getConnection();
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, userID);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				if(rs.getString("userProfile").equals("") || rs.getString("userProfile") == null) {
					return "http://localhost:8090/UserChat/images/icon.png";
				}
				return "http://localhost:8090/UserChat/upload/" +rs.getString("userProfile");
				
			}
		} catch (Exception e) {
			System.out.println("getProfile()메소드 오류"+e);
		} finally {
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(conn !=null) conn.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return "http://localhost:8090/UserChat/images/icon.png"; //데이터베이스 오류 
	}
}
