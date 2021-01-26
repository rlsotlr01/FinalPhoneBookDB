package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


/**
 * @작성자 : 김동윤
 * @작성일 : 2021. 1. 21.
 * @filename : LocalNumberDAO.java
 * @package : dao
 * @description : 지역번호를 갖고 있는 데이터베이스을 접근하는 DAO 프로그램입니다.
 */
public class LocalNumberDAO {

//	자바와 DB 사이를 잇는 커넥션을 생성하는 함수입니다.
	private static Connection getConnection(){
//		커넥션 객체를 담을 con 변수를 만들어줍니다.
		Connection con 		= null;
//		오라클 서버의 주소와 유저 아이디, 그리고 비밀번호를 준비해둡니다.
		String url 			= "jdbc:oracle:thin:@localhost:1521:xe"; 
		String user 		= "ora_user"; // (열쇠)
		String password 	= "hong";
		
		try {
//			con에 DB와 자바 사이의 커넥션 오브젝트를 생성하여 담아줍니다.
			con = DriverManager.getConnection(url, user, password);			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
//		커넥션 오브젝트를 반환해줍니다.
		return con;
	} // 이 getConnection 은 EmpDAO 에서만 쓰일 것.
	
//	커넥션, pstmt(트럭), ResultSet(결과값 받아오는 것) 모두를 닫아주는 함수입니다.
	private static void close(Connection con
			 , PreparedStatement pstmt
			 , ResultSet rs) {
		try {
//			rs, pstmt, con 모두 null이 아닌 조건 하에 close를 진행해줍니다.
//			그러지 않을 경우 nullPointException 이 발생할 수 있습니다.
			if(rs != null) {
				rs.close();
			}
			if(pstmt != null) {
				pstmt.close();
			}
			if(con != null) { 
				con.close();
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
//	DB에 담긴 모든 지역번호를 어레이리스트 형식으로 불러오는 함수입니다.
	public ArrayList<String> selectAll_localnumber(){
		
//		지역번호를 담을 어레이리스트를 마련해줍니다.
		ArrayList<String> localNumber = new ArrayList<String>();
//		DB와 자바 사이의 커넥션을 만듭니다.
		Connection con = getConnection();
//		pstmt, rs, sql 을 담을 변수를 미리 준비해둡니다.
		PreparedStatement pstmt 	= null;
		ResultSet rs 				= null;
		StringBuilder sql 			= new StringBuilder();
		
//		DB에서 처리할 SQL 명령어 구문을 sql에 담습니다.
		sql.append("SELECT localnumber		");
		sql.append("  FROM localnumberlist	");
		
		try {
//			pstmt(트럭)을 만들어 그 안에 sql 구문을 String으로 변환해준 것을 담아줍니다.
			pstmt = con.prepareStatement(sql.toString());
//			그리고 그 구문을 DB로 가 실행하고, 결과값을 rs(ResultSet)에 담아냅니다.
			rs = pstmt.executeQuery();
			while(rs.next()) {
//				DB에 localnumber 라는 항목의 값들을 localNumber 어레이리스트에 하나하나 담아줍니다.
				localNumber.add(rs.getString("localnumber"));
			}
		}catch(SQLException e) {
			System.out.println("데이터베이스에 문제가 있습니다. 오라클에 가서 데이터베이스를 확인해주세요.");
			e.printStackTrace();
		}finally {
//			con, pstmt, rs를 닫아줍니다.
			close(con, pstmt, rs);
		}
		
//		마지막으로 지역번호가 담긴 어레이리스트를 반환합니다.
		return localNumber;
	}
	
}
