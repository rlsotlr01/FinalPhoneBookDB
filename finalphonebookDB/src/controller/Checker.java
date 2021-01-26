package controller;

import java.util.ArrayList;

import service.UserService;


/**
 * @작성자 : 김동윤
 * @작성일 : 2021. 1. 23.
 * @filename : Checker.java
 * @package : controller
 * @description : 각각의 변수에 대입될 값들에 필요한 조건들을 확인하는 기능을 하는 함수들을 모아놓은 클래스입니다.
 * 				  (핸드폰번호, 그룹번호)
 */
public class Checker {
	public UserService us = new UserService();
//	그룹이  1~3 안에 속하는지 판단하는 boolean 함수입니다.
	public boolean groupChecker(String groupName) {
		return ((groupName).matches("1")) || (groupName.matches("2")) || (groupName.matches("3"));
	}

//	연락처 체크하는 기능 (핸드폰 번호 또는 집 전화번호)
	public boolean numberChecker(String number) {

//		연락처가 10자리 이하면 안된다. (F)
		if (number.length() < 10) {
			return false;
		}

		boolean phoneNumberChecker;

//		연락처가 11자리 이여야 한다. (T)
		boolean elevenCheck = (((number).length()) == 11); // 11자리인지 체크
//		핸드폰번호 앞자리 3자리가 010이어야 한다. (T)
		boolean frontThreeCheck = ((number.substring(0, 3)).matches("010")); // 앞에 세자리 핸드폰 번호(010)인지 체크

		char[] numberArray = number.toCharArray();
		boolean normalNumberChecker;

//		전화번호 앞자리 지역번호 확인하기 위해 지역번호 배열을 만들어준다.
		ArrayList<String> localNumber = new ArrayList<String>(); // 지역번호 배열을 생성한다.
		localNumber = us.selectAll_localnumber();

//		집전화번호는 10자리여야만 한다.
		boolean tenCheck = ((number.length()) == 10);
//		앞 지역번호 2자리 또는 3자리를 확인하는 boolean 결과를 담는 변수를 담는다.
		boolean frontThreeLocalCheck;

//		앞 지역번호 2자리 또는 3자리를 확인하여 지역번호를 갖고 있을 땐 true 를 반환한다.
		if (((number.substring(0, 2)).matches("02")) || (localNumber.contains(number.substring(0, 3)))) {
			frontThreeLocalCheck = true;
		} else { // 그렇지 않으면 false 를 리턴한다.
			frontThreeLocalCheck = false;
		} // 지역번호만 받아들이기.

//		입력받은 값이 모두 숫자인지 판단하는 변수입니다.
		boolean allDigitCheck = true;
//		받은 글자를 배열로 돌려 각각의 글자가 숫자인지 판단합니다. (숫자가 아니면 false를 바로 리턴합니다.)
		for (char alphabet : numberArray) {
			if (!Character.isDigit(alphabet)) {
				allDigitCheck = false;
				break;
			}
		}

//		핸드폰 번호인지를 판단하는 변수입니다. 11자리, 앞 3자리는 010, 모두 숫자로 이루어졌는지 확인합니다.
		phoneNumberChecker = elevenCheck && frontThreeCheck && allDigitCheck;
//		집전화번호인지 판단하는 변수입니다. 10자리, 앞자리는 지역번호, 모두 숫자로 이루어졌는지 확인합니다.
		normalNumberChecker = tenCheck && frontThreeLocalCheck && allDigitCheck; // 집전화번호인지 검증

//		폰번호이거나 집전화번호인지 증명된 것들만 True 를 반환합니다.
		return phoneNumberChecker || normalNumberChecker; // 11 숫자이며, 앞에 3자리가 핸드폰번호, 그리고 모두 숫자일 경우만 true 이다.
	}

}
