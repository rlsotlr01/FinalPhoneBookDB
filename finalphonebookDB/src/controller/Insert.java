package controller;

import java.util.Scanner;

import view.UserView;
import vo.UserVO;

/**
 * @작성자 : 김동윤
 * @작성일 : 2021. 1. 23.
 * @filename : Insert.java
 * @package : controller
 * @description : 들어가는 값들을 Checker을 통해 확인하고 
 * 				    조건에 해당하는 값들만을 변수와 객체에 대입해주는 함수들을 모아놓은 클래스입니다.
 * 				  (이름, 핸드폰번호, 주소, 그룹, 연락처회원)
 */
public class Insert {
	public Checker checker = new Checker();
	public UserView uv = new UserView();
	
	
//	이름 입력 함수
	public String insertName(Scanner scan) {
		String name;
		while(true) {
//			이름 기입란을 출력합니다.
			uv.inputName();
			name = scan.nextLine();
			if(name.length() == 0) {
				
//				아무것도 입력되지 않았을 때 이를 알리기 위한 출력문입니다.
				uv.noInput();
				continue;
			}
			break;
		}
		return name;
	}
	
//	핸드폰 입력 함수 (핸드폰인지 집전화인지 체크하는 "numberChecker" 함수를 이용합니다.)
	public String insertPhoneNumber(Scanner scan) {
		String phoneNumber;
		while (true) {
			uv.inputNumber();
			if (checker.numberChecker(phoneNumber = scan.nextLine())) {
				break;
			}else {
//				연락처가 유효하지 않음을 알리는 내용을 출력합니다.
				uv.invalidNumber();
			}
		}
		return phoneNumber;
	}
	
	public String insertAddress(Scanner scan) {
		String address;
		while(true) {
			uv.inputAddress();
			address = scan.nextLine();
			if(address.length() == 0) {
//				아무것도 입력되지 않았을 때 이를 알리기 위한 출력문입니다.
				uv.noInput();
				continue;
			}
			break;
		}
		return address;
	}

//	그룹 입력 함수 (그룹이 1~3에 속하는지 체크하는 "groupChecker" 함수를 이용합니다.)
	public String insertGroup(Scanner scan) {
		String groupno;
		while (true) {
			uv.inputGroup();
			if (checker.groupChecker(groupno = scan.nextLine())) {
				break;
			}else {
				uv.invalidGroup();
			}
		}
		return groupno;
	}

//	개인 정보를 조건들을 체크 후 입력받아 유저(연락처) 하나의 객체를 완성하는 함수입니다.
	public UserVO makeNewUser(Scanner scan) {
		UserVO user = new UserVO();

//		이름을 받습니다.
		String name = insertName(scan);
		
//		번호를 받습니다.
		String phoneNumber = insertPhoneNumber(scan);

//		주소를 받습니다.
		String address = insertAddress(scan);

//	        소속을 받습니다.
		String groupno = insertGroup(scan);
		user.setName(name);
		user.setGroup(groupno);
		user.setAddress(address);
		user.setPhoneNumber(phoneNumber);

		return user;
	}
}
