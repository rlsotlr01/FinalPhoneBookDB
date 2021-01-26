package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import view.UserView;
import vo.UserVO;


/**
 * @작성자 : 김동윤
 * @작성일 : 2021. 1. 21.
 * @filename : UserDAO.java
 * @package : dao
 * @description : 유저 데이터에 접근하고 처리하는 기능을 가진 클래스입니다.
 */
public class UserDAO {
	public UserView uv = new UserView();

//	DB와 자바 사이의 커넥션을 만들어 주는 함수입니다.
	private Connection getConnection(){
		Connection con = null;
//		url 로는 오라클 서버까지의 경로를 지정해줍니다.
		String url 			= "jdbc:oracle:thin:@localhost:1521:xe"; 
//		오라클 서버에 가입된 유저의 아이디와 비밀번호를 지정해줍니다.
		String user 		= "ora_user"; // (열쇠)
		String password 	= "hong";
		
//		경로와 아이디 비밀번호로 커넥션을 만들어주며, SQL 예외처리를 해줍니다.
		try {
			con = DriverManager.getConnection(url, user, password);			
		}catch(SQLException e) {
//			DB에 문제가 있음을 알리는 내용을 출력합니다.
			uv.DBFail();
			e.printStackTrace();
		}
		
		return con;
	} // 이 getConnection 은 EmpDAO 에서만 쓰일 것.
	
//	con, pstmt, rs 을 한꺼번에 닫을 수 있는 함수입니다.
	private void close(Connection con
			 , PreparedStatement pstmt
			 , ResultSet rs) {
		try {
//			null 이 아닐 경우에만 닫아줍니다.
//			(nullpointException 방지)
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
//			DB에 문제가 있음을 알리는 내용을 출력합니다. (SQLException)
			uv.DBFail();
			e.printStackTrace();
		}
	}	
	
//	con과 pstmt를 닫아주는 함수입니다.
	private void close(Connection con
			, PreparedStatement pstmt) {
//		null이 아닐 경우에만 닫아줍니다.
//		(nullPointException 방지)
		try {
			if(pstmt != null) {
				pstmt.close();
			}
			if(con != null) { 
				con.close();
			}
		}catch(SQLException e) {
//			DB에 문제가 있음을 알리는 내용을 출력합니다. (SQLException)
			uv.DBFail();
			e.printStackTrace();
		}
			
	}
	
//	모든 유저를 찾아 어레이리스트에 담아주는 함수입니다.
	public ArrayList<UserVO> selectAll(){
//		유저 목록을 받아들일 어레이리스트를 만들어줍니다.
		ArrayList<UserVO> userList 	= new ArrayList<UserVO>();
//		커넥션을 통해 DB와 자바 사이를 잇습니다.
		Connection con 				= getConnection();
		PreparedStatement pstmt 	= null;
		ResultSet rs 				= null;
//		DB에 입력할 sql 구문을 담을 sql 변수를 만듭니다.
		StringBuilder sql 			= new StringBuilder();
		
//		ORACLE SQL 에서 Select 구문을 sql에 삽입합니다.
		sql.append("SELECT p.name				");
		sql.append("	 , p.phonenumber		");
		sql.append("	 , p.address			");
		sql.append("     , p.groupno			");
		sql.append("     , g.groupnm			");
		sql.append("  FROM phonebook p			");
		sql.append("	 , group_table g		");
		sql.append(" WHERE p.groupno = g.groupno");
		try {
//			prepareStatement 의 파라미터로는 String 만 받을 수 있으므로,
//			StringBuilder -> String 으로 변환해줍니다.
			pstmt = con.prepareStatement(sql.toString());
//			그 sql을 실행시켜 결과를 받아들이고 이를 rs에 담습니다.
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
//				유저 객체를 생성하고, 그 안에 rs가 받은 Setting 합니다.
				UserVO user 		= new UserVO();
				user.setName(rs.getString("name"));
				user.setPhoneNumber(rs.getString("phonenumber"));
				user.setAddress(rs.getString("address"));
				user.setGroup(rs.getString("groupnm"));
//				그리고 그 유저 객체를 목록에 추가합니다.
				userList.add(user);
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close(con, pstmt, rs);
		}
		
//		결과적으로 만들어진 연락처 유저목록을 반환합니다.
		return userList;
	}
	
//	새로운 유저를 넣어주는 함수입니다. 결과값으로는 들어간 개인정보의 갯수가 반환됩니다.
	public int insertUser(UserVO user) {
		int rowcnt 				= 0;
//		DB와 자바를 커넥션을 통해 잇습니다.
		Connection con = getConnection();
		PreparedStatement pstmt = null;
		
//		DB에 명령할 SQL 구문을 sql 변수에 담습니다.
		StringBuilder sql = new StringBuilder();
		sql.append("insert into phonebook(name,"
				+ " phonenumber, address, groupno)");
		sql.append("			VALUES(?,?,?,?)");
		
		try {
//			pstmt 에 sql 구문을 담습니다.
			pstmt = con.prepareStatement(sql.toString());
//			?에 기입한 개인정보를 하나하나 넣어줍니다.
			pstmt.setString(1, user.getName());
			pstmt.setString(2, user.getPhoneNumber());
			pstmt.setString(3, user.getAddress());
			pstmt.setString(4, user.getGroup());
			
//			pstmt 를 실행시켜 입력받은 정보를 DB에 하나하나 기입하고,
//			기입된 정보의 갯수를 출력합니다.
			rowcnt = pstmt.executeUpdate();
			
//			잘 들어갔는지 출력해도록 해줍니다.
			if(rowcnt<=0) {
//				값이 정상적으로 DB에 들어가지 않았음을 출력합니다.
				uv.rowcntFail();
			}
		}catch(SQLException e) {
//			DB에 문제가 있음을 알리는 내용을 출력합니다. (SQLException)
			uv.DBFail();
			e.printStackTrace();
		}finally {
			
		}
		
		return rowcnt;
	}
	
//	이름으로 유저를 검색하여 그 이름에 해당하는 사람들을 어레이리스트에 담아주는 함수입니다.
	public ArrayList<UserVO> selectByName(String name) {
		
//		커넥션을 통해 자바와 DB를 잇습니다.
		Connection con 			= getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs 			= null;
		ArrayList<UserVO> userList = new ArrayList<UserVO>();
		
//		DB에 처리할 명령어를 sql 변수에 담습니다.
		StringBuilder sql = new StringBuilder();
		sql.append("select p.name					");
		sql.append("     , p.phonenumber				");
		sql.append("     , p.address					");
		sql.append("     , p.groupno					");
		sql.append("     , g.groupnm					");
		sql.append("  from phonebook p, group_table g	");
		sql.append(" where p.name like ?			");
		sql.append("   and p.groupno = g.groupno		");
		
		try {
//			pstmt 에 sql 을 String 형식으로 변환시켜 집어 넣습니다.
			pstmt = con.prepareStatement(sql.toString());
//			Like 구문이기에 ?에 들어갈 내용 앞뒤에 %를 붙여줍니다.
			pstmt.setString(1, "%"+name+"%");
//			실행시켜 결과값을 rs가 받습니다.(ResultSet)
			rs = pstmt.executeQuery();
			
//			결과값을 모두 돌아가도록 하는 반복문입니다.
			while(rs.next()) {
//				유저 객체에 받은 결과값들을 하나하나 넣습니다.
				UserVO user = new UserVO();
				user.setName(rs.getString("name"));
				user.setPhoneNumber(rs.getString("phonenumber"));
				user.setAddress(rs.getString("address"));
				user.setGroup(rs.getString("groupnm"));
				
//				그 객체를 유저리스트 안에 넣습니다.
				userList.add(user);
			}
//			유저리스트를 반환해줍니다.
			return userList;
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			close(con,pstmt,rs);
		}
		return userList;
		
	}
	
//	데이터베이스에 그 핸드폰 번호를 가진 유저가 있는지 확인하는 함수
//	(한 핸드폰번호는 Primary Key 이므로 1명만이 그 핸드폰 번호를 가지고 있습니다.
//	 그래서 어레이리스트가 아닌 유저VO 객체를 반환합니다.)
	public UserVO selectByPhoneNumber(String phoneNumber) {
//		자바와 DB를 잇는 커넥션을 만들어줍니다.
		Connection con 			= getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs 			= null;
//		검색한 유저를 받아들일 유저객체를 만듭니다.
		UserVO user = new UserVO();
		
//		DB에 처리하고 싶은 명령어를 sql 변수에 담습니다.
//		참고로 테이블이 2개이기 때문에 ANSI 조인으로 이어줍니다.
//		(혹여나 오라클이 아닌 다른 DB를 이용할 경우를 대비해서)
		StringBuilder sql = new StringBuilder();
		sql.append("select p.name					");
		sql.append("     , p.phonenumber				");
		sql.append("     , p.address					");
		sql.append("     , g.groupnm					");
		sql.append("  from phonebook p, group_table g	");
		sql.append(" where p.phonenumber = ?			");
		sql.append("   and p.groupno = g.groupno		");
		
		
		try {
//			pstmt(트럭)에 sql 구문을 String 으로 변환해준 값을 집어 넣습니다.
			pstmt = con.prepareStatement(sql.toString());
//			그리고 ?에 내가 검색하고 싶은 휴대폰 번호를 넣습니다.
			pstmt.setString(1, phoneNumber);
//			그리고 pstmt 를 통해 DB에 sql 구문을 돌려 실행시키고
//			결과값을 rs(ResultSet)에 담습니다.
			rs = pstmt.executeQuery();
			
//			반복문을 통해 결과값 모두를 돌아다닙니다.
			while(rs.next()) {
//				결과값에 해당하는 각각의 컬럼명에 해당하는 정보를 
//				유저 객체의 알맞은 변수에 대입합니다.
				user.setName(rs.getString("name"));
				user.setPhoneNumber(rs.getString("phonenumber"));
				user.setAddress(rs.getString("address"));
				user.setGroup(rs.getString("groupnm"));
			}
//			그 휴대폰번호에 해당하는 유저를 반환합니다.
			return user;

		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			close(con,pstmt,rs);
		}
		return user;
		
	}
	
//	해당 유저를 지우는 함수입니다. 결과값으로는 지워진 정보의 갯수가 반환됩니다.
	public int deleteUser(UserVO user) {
		int rowcnt 				= 0;
//		커넥션을 통해 자바와 DB를 잇습니다.
		Connection con = getConnection();
		PreparedStatement pstmt = null;
		
//		해당 연락처의 유저를 지우는 delete sql 구문을 sql 변수에 집어넣습니다.
		StringBuilder sql 		= new StringBuilder();
		sql.append("DELETE phonebook		");
		sql.append(" WHERE phonenumber = ?	");

		try {
//			pstmt(트럭)에 처리할 sql 명령어 구문을 담습니다.
			pstmt 				= con.prepareStatement(sql.toString());
//			그리고 ?에 지우고자 하는 유저의 핸드폰번호를 넣습니다.
			pstmt.setString(1, user.getPhoneNumber());
//			지워진 개인정보의 갯수가 rowcnt에 담깁니다.
			rowcnt 				= pstmt.executeUpdate();
			
//			개인정보가 잘 지워졌는지 확인하는 출력문을 만듭니다.
			if(rowcnt <= 0) {
//				DAO 에서 값이 정상적으로 DB에 들어가지 않았음을 출력합니다.
				uv.rowcntFail();
			}
			
		}catch(SQLException e) {
			uv.DBFail();
			e.printStackTrace();
		}finally {
			close(con,pstmt);
		}
		
		return rowcnt;
	}
	
//	기존의 연락처의 회원(old_user)을 새로운 개인정보(new_user)로 수정해주는 함수입니다. 
//	결과값으로는 수정된 개인정보의 갯수를 반환합니다.
	public int updateUser(UserVO old_user, UserVO new_user) {
		int rowcnt = 0;
//		자바와 DB를 커넥션으로 잇습니다.
		Connection con 				= getConnection();
		PreparedStatement pstmt 	= null;
		StringBuilder sql 			= new StringBuilder();
		String new_name 			= new_user.getName();
		String new_number 			= new_user.getPhoneNumber();
		String new_address 			= new_user.getAddress();
		String new_groupno 			= new_user.getGroup();
		String old_phonenumber 		= old_user.getPhoneNumber();
		
//		sql변수 안에 DB에서 처리할 명령어를 담습니다.
//		UPDATE SET을 활용합니다.
		sql.append("UPDATE phonebook	");
		sql.append("   SET name = ?		");
		sql.append(" , phonenumber = ?");
		sql.append(" , address = ?");
		sql.append(" , groupno = ?");
		sql.append(" WHERE phonenumber = ?");
		
		try {
//			pstmt(트럭)에 기입된 sql 구문을 String 으로 변환한 값을 삽입합니다.
			pstmt = con.prepareStatement(sql.toString());
			
//			그리고 ?에 수정하고 싶은 개인정보를 하나하나 집어넣습니다.
			pstmt.setString(1, new_name);
			pstmt.setString(2, new_number);
			pstmt.setString(3, new_address);
			pstmt.setString(4, new_groupno);
			pstmt.setString(5, old_phonenumber);
//			DB에서 sql 구문을 실행합니다.
			
			rowcnt = pstmt.executeUpdate();
			
//			잘 들어갔는지 출력해도록 해줍니다.
			if(rowcnt<=0) {
//				값이 정상적으로 DB에 들어가지 않았음을 출력합니다.
				uv.rowcntFail();
			}
			
		}catch(SQLException e) {
			uv.DBFail();
			e.printStackTrace();
		}finally {
			close(con,pstmt);
		}
//		수정된 값들의 갯수를 반환합니다.
		return rowcnt;
	}
}
