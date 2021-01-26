package controller;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import service.UserService;
import view.UserView;
import vo.UserVO;

/**
 * @작성자 : 김동윤
 * @작성일 : 2021. 1. 21.
 * @filename : UserController.java
 * @package : controller
 * @description : 데이터베이스를 연동한 전화번호부에서 필요한 기능들을 모아 각각의 큰 기능을 구현한 프로그램입니다.
 */
public class UserController {

	public UserService us = new UserService();
//	DB를 사용하기 위한 기능들을 묶어놓은 UserService 클래스를 사용하기 위해 오브젝트로 생성해줍니다.
	public UserView uv = new UserView();
//	필요한 출력문을 출력하는 기능을 하는 UserView 클래스를 사용하기 위해 오브젝트로 생성해줍니다.
	public Insert insert = new Insert();
//	변수와 객체에 값을 대입해주는데 있어서 값들을 확인하고 넣도록 하는 Insert 클래스를 사용하기 위해 오브젝트를 생성해줍니다.


//	모든 연락처 회원을 불러와 출력하는 함수입니다.
	public void selectAll() {
		ArrayList<UserVO> userList = us.selectAll();
//		모든 연락처 회원의 유저를 DB로부터 어레이리스트(userList)로 옮겨 담습니다.
		if (userList.size() == 0) {
//			DB에 아무 회원도 존재하지 않을 경우
//			등록된 회원이 없음을 출력합니다.
			uv.noUser();
		}else {
//			DB에 적어도 한명의 회원이 존재할 경우
//			모든 회원의 목록을 출력합니다.
			uv.printAll(userList);
		}
		
	}


//	유저를 연락처에 추가하는 함수입니다.
	public void insertUser(Scanner scan) {
		UserVO old_user = new UserVO();
		UserVO new_user = new UserVO();
		
//		등록할 회원의 정보를 입력하라는 출력문을 출력합니다.
		uv.register();
		
//		새로운 유저를 입력합니다.
		new_user = insert.makeNewUser(scan);

//	         기존의 목록에 위 번호를 가진 연락처가 있는지 조사한다.(만약 있으면 덮어씌우기)
		old_user = us.selectByPhoneNumber(new_user.getPhoneNumber());

//		기존의 목록에 위 번호를 가진 연락처가 있다면 덮어씌울지 여부를 물어보며,
//		Y/y 를 선택할 경우 덮어씌우고, N/n 을 선택할 경우 덮어씌우지 않습니다.
		if (old_user.getPhoneNumber() != null) {

			System.out.println(old_user.toString());
//			해당 유저가 있을 시 덮어씌우기를 할지 물어보는 출력문을 출력합니다.
			uv.alreadyExist();
			String choice = scan.nextLine();
			if (choice.matches("Y") || choice.matches("y")) {
//				성공적으로 덮어씌워졌을 경우
				uv.duplicated();
				us.deleteUser(old_user);
				us.insertUser(new_user);

			} else if (choice.matches("N") || choice.matches("n")) {
				uv.notDuplicated();
			} else {
				uv.wrongTyping_yn();
			}
		} else {
			us.insertUser(new_user);
			uv.registerSuccess();
		}

	}

//	해당 이름으로 저장된 연락처를 가져오는 기능입니다.
	public void selectByName(String name) {
		
		ArrayList<UserVO> userList = us.selectByName(name);
		uv.printAll(userList);
	}

//	삭제하는 기능을 하는 함수입니다.
	public void deleteUser(Scanner scan) {

		String name;
		ArrayList<UserVO> userList = new ArrayList<UserVO>();
		
//		DB에 아무 연락처도 저장되어 있지 않을 경우 바로 함수를 끝냅니다.
		ArrayList<UserVO> usercheck = us.selectAll();
		if (usercheck.size() == 0) {
//			등록된 회원이 없음을 출력합니다.
			uv.noUser();
			return;
		}
		
		System.out.println("\n삭제할 회원의 정보를 입력하세요.");

//		이름을 받는다
		name = insert.insertName(scan);
		userList = us.selectByName(name);

//		기입한 이름의 연락처리스트가 존재하지 않으면 
		if (userList.isEmpty()) {
//			해당 연락처의 회원이 없음을 출력합니다.
			uv.noUserWithName();
			return;
		}
//		기입한 이름의 연락처 리스트가 존재할 경우 : 그 이름의 사람들 목록을 출력하고, 선택을 받아 삭제하도록 한다.
		while (true) {
//			삭제할 인덱스를 입력하라는 내용을 출력합니다.
			uv.insertDeleteIndex();
//			인덱스와 함께 연락처가 출력됩니다.
			uv.printAllWithIndex(userList);
//			목록을 선택하는 과정에서의 예외를 제거해준다.
			try {
				int choice;
				while (((choice = scan.nextInt() - 1) >= userList.size()) || (choice < -1)) {
//					입력한 숫자가 잘못됨을 출력합니다.
					uv.wrongTyping_number_delete();
				}
				scan.nextLine();
				if (choice == -1) {
//					메인메뉴로 돌아감을 출력합니다.
					uv.returnMain();
					break;
				}
//				입력받은 값이 오류가 없고 올바른 값이면 해당 연락처를 삭제한다.
				try {
//					선택한 유저를 삭제하고, 성공적으로 삭제됨을 출력합니다.
					us.deleteUser(userList.get(choice));
					uv.deleted();
					break;
				} catch (IndexOutOfBoundsException e) {
//					잘못된 숫자를 입력함을 알립니다.
					uv.wrongTyping_number_delete();
				}
			} catch (InputMismatchException e) {
//				문자가 아닌 숫자를 입력하라는 출력문을 날리고, 값을 다시 받습니다.
				scan.nextLine();
				uv.insertNumber();
				continue;
			}
		}
	}

	public void updateUser(Scanner scan) {
		String name;
		ArrayList<UserVO> userList = new ArrayList<UserVO>();
		
//		DB에 아무 연락처도 저장되어 있지 않을 경우 바로 함수를 끝냅니다.
		ArrayList<UserVO> usercheck = us.selectAll();
		if (usercheck.size() == 0) {
//			등록된 회원이 없음을 출력합니다.
			uv.noUser();
			return;
		}
		
//		수정할 회원의 정보를 입력하라는 출력문을 출력합니다.
		uv.editUser();
		UserVO old_user = new UserVO();
		
//		이름을 받는다
		name = insert.insertName(scan);
		userList = us.selectByName(name);

//		만약 검색한 이름의 연락처가 없을 때 함수를 끝냅니다.
		if (userList.isEmpty()) {
//			해당 이름의 연락처가 없음을 알리는 내용을 출력합니다.
			uv.noUserWithName();
			return;
		}
//		검색한 이름의 연락처가 있을 경우 그 이름의 연락처들을 출력하고 선택을 받습니다.
		while (true) {
//			수정할 회원의 정보를 입력하라는 출력문을 출력합니다.
			uv.insertEditIndex();
//			그리고 해당 회원들의 목록을 인덱스와 함께 출력합니다.
			uv.printAllWithIndex(userList);
//			입력값들을 걸러냅니다.
			try {
				int choice;
				while (((choice = scan.nextInt() - 1) >= userList.size()) || (choice < -1)) {
//					입력한 숫자가 잘못됨을 출력합니다. (수정기능 안에서의 사용)
					uv.wrongTyping_number_update();
				}
				scan.nextLine();
				if (choice == -1) {
//					메인메뉴로 돌아감을 출력합니다.
					uv.returnMain();
					break;
				}
				try {
					old_user = userList.get(choice);
					UserVO new_user = new UserVO();
					
//        			이름 조건 고려하며 입력
					new_user = insert.makeNewUser(scan);

					System.out.println(new_user.toString());

//					수정하려고 하는 연락처를 가진 사람이 이미 존재할 경우 : 
//					덮어씌우기를 할지 말지 물어보고 덮어씌우기 기능을 활용한다.					
					UserVO user = us.selectByPhoneNumber(new_user.getPhoneNumber());
					if (user.getPhoneNumber() != null) {
//						해당 유저가 있을 시 덮어씌우기를 할지 물어보는 출력문을 출력합니다.
						uv.alreadyExist();
						String choice2 = scan.nextLine();
						if (choice2.matches("Y") || choice2.matches("y")) {
//							덮어씌우기를 완성했을 시 정보가 성공적으로 수정됨을 알리는 출력문을 출력합니다.
							uv.duplicated();
//							기존의 핸드폰번호를 가진 pk 로 인해서 새로운 값이 못들어가기 때문에
//							기존의 회원을 먼저 지우고, 새로 입력받은 회원을 넣는다.
							us.deleteUser(user);
							us.updateUser(old_user, new_user);
							break;
						} else if (choice2.matches("N") || choice2.matches("n")) {
//							덮어씌우기를 하지 않았을 때, 하지 않음을 알리는 출력문을 출력합니다.
							uv.notDuplicated();
							break;
						} else {
//							y/n 키가 아닌 다른 키를 입력했을 때 잘못 입력했음을 알리는 출력문을 출력합니다.
							uv.wrongTyping_yn();
						}
					} else {
//						기존의 회원을 새로운 회원으로 수정합니다.
						us.updateUser(old_user, new_user);
//						연락처의 수정이 완료됨을 알리는 내용을 출력합니다.
						uv.updateSuccess();
						break;
					}

				} catch (IndexOutOfBoundsException e) {
//					선택한 번호가 그 이름에 해당하는 회원의 목록의 인덱스를 벗어날 경우
//					입력한 숫자가 잘못됨을 출력합니다. (삭제기능 안에서의 사용)
					uv.wrongTyping_number_update();
				}
			} catch (InputMismatchException e) {
//				숫자가 아닌 문자나 다른 것을 넣어주었을 경우
//				숫자를 다시 입력받도록 한다.
				scan.nextLine();
				uv.insertNumber();
			}
		}
	}
}
