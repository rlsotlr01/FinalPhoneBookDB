package service;

import java.util.ArrayList;

import dao.LocalNumberDAO;
import dao.UserDAO;
import vo.UserVO;


/**
 * @작성자 : 김동윤
 * @작성일 : 2021. 1. 21.
 * @filename : UserService.java
 * @package : service
 * @description : 유저DAO와 지역번호DAO 클래스를 활용하여 컨트롤러에 서비스(보조)를 하는 클래스입니다.
 */
public class UserService {
	
//	연락처 유저DAO와 전화번호 지역번호DAO 의 기능을 사용하기 위해 오브젝트를 생성합니다.
	public UserDAO userDAO 				= new UserDAO();
	public LocalNumberDAO localNumberDAO= new LocalNumberDAO();
	
//	DB 내의 모든 정보를 가져오는 기능을 하는 함수입니다.
	public ArrayList<UserVO> selectAll() {
//		정보를 담을 유저 어레이리스트를 만듭니다.
		ArrayList<UserVO> userList 		= new ArrayList<UserVO>();
//		그 유저 어레이리스트에 DB에서 불러온 값들을 모두 담습니다.
		userList = userDAO.selectAll();
//		DB에서 불러와 자바의 어레이리스트에 담은 값을 반환합니다.
		return userList;		
	}
	
//	지역번호를 모두 가져오는 기능을 하는 함수입니다.
	public ArrayList<String> selectAll_localnumber() {
//		지역번호를 담을 어레이리스트 객체를 만들고, 그 안에 DB에서 불러온 지역번호들을 담습니다.
		ArrayList<String> localNumber 	= new ArrayList<String>();
		localNumber = localNumberDAO.selectAll_localnumber();
		
//		지역번호 어레이리스트를 반환합니다.
		return localNumber;
	}
	
//	새로운 유저 객체를 집어 넣는 함수입니다.
	public int insertUser(UserVO user) {
//		새로운 유저를 DB안에 집어넣고,
//		성공적으로 들어간 값들의 갯수를 rowcnt 에 담습니다.
		int rowcnt = 0;
		rowcnt = userDAO.insertUser(user);
		
//		성공적으로 들어온 값들의 갯수를 반환합니다.
		return rowcnt;
	}

//	해당 이름을 가진 연락처를 찾아내어 어레이리스트 형식으로 반환하는 함수입니다.
	public ArrayList<UserVO> selectByName(String name){
//		유저 어레이리스트를 만듭니다.
		ArrayList<UserVO> userList 	= new ArrayList<UserVO>();
//		유저 어레이리스트에 해당 이름인 유저들의 목록을 DB에서 찾아와 집어넣습니다.
		userList = userDAO.selectByName(name);
//		해당 이름을 가진 유저들의 어레이리스트를 반환합니다.
		return userList;
	}
	
//	유저를 DB에서 지우는 기능을 하는 함수입니다.
	public int deleteUser(UserVO user) {
//		DB에서 해당 유저를 지우고,
//		지운 값들의 갯수를 rowcnt에 집어넣어 반환합니다.
		int rowcnt 	= 0;
		rowcnt = userDAO.deleteUser(user);
		return rowcnt;
	}
	
//	핸드폰번호로 검색하는 기능을 하는 함수입니다.
	public UserVO selectByPhoneNumber(String phoneNumber) {
//		새로운 유저 객체를 형성하고,
//		그 유저에 해당 연락처를 가진 유저를 DB에서 찾아서 집어넣습니다.
		UserVO user = new UserVO();
		user = userDAO.selectByPhoneNumber(phoneNumber);
//		그리고 해당 전화번호를 가진 유저를 반환합니다.
		return user;
	}
	
//	기존의 유저를 새로운 유저로 업데이트 하는 함수입니다.
	public int updateUser(UserVO old_user, UserVO new_user) {
//		DB안의 값들을 수정하고,
//		수정된 값들의 갯수를 rowcnt 변수에 담습니다.
		int rowcnt	= 0;	
		rowcnt = userDAO.updateUser(old_user, new_user);

//		수정된 값들의 갯수를 반환합니다.
		return rowcnt;
	}
	
}
