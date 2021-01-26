package view;

import java.util.ArrayList;

import vo.UserVO;

/**
 * @작성자 : 김동윤
 * @작성일 : 2021. 1. 21.
 * @filename : UserView.java
 * @package : view
 * @description : 프린트를 하는 함수만 모아놓은 클래스입니다.
 */
public class UserView {

	
// --------- 1. 여기부턴 메인메뉴에서 쓰는 view 기능 ------------  //
	
//	초기 화면(메뉴)를 출력하는 함수입니다.
	public void printMenu() {
		System.out.println();
		System.out.println("---------------------------");
		System.out.println("다음 메뉴 중 하나를 선택하세요.");
		System.out.println();
		System.out.println("1. 회원 추가");
		System.out.println("2. 회원 목록 보기");
		System.out.println("3. 회원 정보 수정하기");
		System.out.println("4. 회원 삭제");
		System.out.println("5. 종료");
		System.out.println("---------------------------");
		System.out.println();
	}
	
//	프로그램이 종료됨을 출력하는 함수입니다.
	public void printEnd() {
		System.out.println("---------------------------");
		System.out.println("프로그램이 종료됩니다.");
		System.out.println("---------------------------");
	}
	
// --------- 1. 여기까진 메인메뉴에서 쓰는 view 기능 ------------  //
	
	
	
// --------- 2. 여기부턴 Controller에서 쓰는 view 기능 ------------  //	
	
//	유저 어레이리스트를 모두 출력하는 함수입니다.
	public void printAll(ArrayList<UserVO> userList) {
		System.out.println();
		System.out.println("---------------------------");
		System.out.println("저장된 연락처 목록");
		System.out.println();
		for (UserVO user : userList) {
//			toString(오버라이딩된 함수)를 이용하여
//			각각의 유저객체의 이름, 연락처, 등으로 순서대로 나열된 String을 만들고,
//			이를 프린트합니다.
			System.out.println(user.toString());
		}
		System.out.println("---------------------------");
		System.out.println();
	}

//	목록에서 선택하는 경우를 위해서 인덱스와 함께 유저 어레이리스트 안의 객체를 모두 출력하는 함수입니다.
	public void printAllWithIndex(ArrayList<UserVO> userList) {
//		향상된 for문을 통해 하나하나 프린트 합니다.
		System.out.println();
		System.out.println("---------------------------");
		System.out.println("저장된 연락처 목록");
		System.out.println();
		for (UserVO user : userList) {
//			그리고 해당 유저의 index 값에 +1을 하여 1부터 인덱스가 시작되도록 해줍니다.
			System.out.print(userList.indexOf(user) + 1 + ". ");
//			그리고 각각 유저객체 내의 정보들을 그 인덱스 뒤에 출력합니다.
			System.out.println(user.toString());
		}
		System.out.println("---------------------------");
		System.out.println();
	}

	
//	회원이 성공적으로 등록되었음을 출력하는 함수입니다.
	public void registerSuccess() {
		System.out.println("---------------------------");
		System.out.println("등록이 완료되었습니다.");
		System.out.println("---------------------------");
	}
	
//	연락처의 수정이 완료됨을 알리는 내용을 출력합니다.
	public void updateSuccess() {
		System.out.println("---------------------------");
		System.out.println("수정이 완료되었습니다.");
		System.out.println("---------------------------");
	}
	
//	등록된 회원이 없음을 출력합니다.
	public void noUser() {
		System.out.println("현재 등록된 회원이 없습니다.");
	}
	
//	해당 이름의 회원이 없음을 출력합니다.
	public void noUserWithName() {
		System.out.println("해당 이름의 연락처는 존재하지 않습니다.");
	}
	
//	등록할 회원의 정보를 입력하라는 출력문을 출력합니다.
	public void register() {
		System.out.println("\n등록할 회원의 정보를 입력하세요.");
		
	}
	
//	해당 유저가 있을 시 덮어씌우기를 할지 물어보는 출력문을 출력합니다.
	public void alreadyExist() {
		System.out.println("기입한 연락처로 저장되어 있는 회원이 이미 존재합니다." + "\n기입한 정보로 덮어 씌우시겠습니까? Y/N");
	}
	
//	덮어씌우기를 완성했을 시 정보가 성공적으로 수정됨을 알리는 출력문을 출력합니다.
	public void duplicated() {
		System.out.println("기입한 회원의 정보로 갱신되었습니다.");
	}

//	덮어씌우기를 하지 않았을 때, 하지 않았음을 알리는 출력문을 출력합니다.
	public void notDuplicated() {
		System.out.println("기입한 회원의 정보로 갱신하지 않고 메인 메뉴로 돌아갑니다.");
	}
	
//	y/n 키가 아닌 다른 키를 입력했을 때 잘못 입력했음을 알리는 출력문을 출력합니다.
	public void wrongTyping_yn() {
		System.out.println("키를 잘못 입력하셨습니다.\nY 또는 N 으로 다시 기입해주세요.");
	}
	
//	입력한 숫자가 잘못됨을 출력합니다. (삭제기능 안에서의 사용)
	public void wrongTyping_number_delete() {
		System.out.println("선택한 수가 유효하지 않습니다. 삭제할 회원의 번호를 다시 입력해주세요.\n0번을 누르시면 메인메뉴로 돌아갑니다.");
	}
	
//	입력한 숫자가 잘못됨을 출력합니다. (수정기능 안에서의 사용)
	public void wrongTyping_number_update() {
		System.out.println("선택한 수가 유효하지 않습니다. 수정할 회원의 번호를 다시 입력해주세요.\n0번을 누르시면 메인메뉴로 돌아갑니다.");
	}
	
//	문자가 아닌 숫자를 입력했을 때 이를 알리는 내용을 출력합니다.
	public void insertNumber() {
		System.out.println("문자가 아닌 숫자를 입력해주세요.");
	}
	
	
//	메인 메뉴로 돌아감을 출력합니다.
	public void returnMain() {
		System.out.println("\n메인 메뉴로 돌아갑니다.\n");
	}
	
//	해당 회원이 삭제됨을 출력합니다.
	public void deleted() {
		System.out.println("\n해당 회원의 연락처가 삭제되었습니다.\n");
	}
	
//	삭제할 회원의 인덱스를 입력하라는 내용을 출력합니다.
	public void insertDeleteIndex() {
		System.out.println("아래 목록 중 삭제할 회원의 번호를 입력하세요. " + "\n(삭제를 원하지 않을 경우 0번을 입력해주세요.)");
	}
	
//	수정할 회원의 인덱스를 입력하라는 내용을 출력합니다.
	public void insertEditIndex() {
		System.out.println("아래 목록 중 수정할 회원의 번호를 입력하세요. " + "\n(수정를 원하지 않을 경우 0번을 입력해주세요.)");
	}
	
//	수정할 회원의 정보를 입력하라는 출력문을 출력합니다.
	public void editUser() {
		System.out.println("\n수정할 회원의 정보를 입력하세요.");
	}
	
// --------- 2. 여기까지 Controller에서 쓰는 view 기능 ------------  //	
	
// --------- 3. 여기부턴 Insert 에서 쓰는 view 기능 ------------  //
	
//	이름 기입란을 출력합니다.
	public void inputName() {
		System.out.print("이름: ");
	}
	
//	아무것도 입력되지 않았을 때 이를 알리기 위한 출력문입니다.
	public void noInput() {
		System.out.println("입력하신 값이 없습니다. 다시 입력해주세요.");
	}
	
//	전화번호 기입란을 출력합니다.
	public void inputNumber() {
		System.out.println("연락처 (ex: 01011111111 or 0312324444): ");
	}
	
//	연락처가 유효하지 않음을 알리는 내용을 출력합니다.
	public void invalidNumber() {
		System.out.println("유효한 연락처가 아닙니다. \n핸드폰 번호의 형식 또는 집전화 번호 형식으로 입력해주세요.");

	}
	
//	주소 기입란을 출력합니다.
	public void inputAddress() {
		System.out.print("주소: ");
	}
	
//	그룹 기입란을 출력합니다.
	public void inputGroup() {
		System.out.print("종류 (ex. 1.가족, 2.친구, 3.기타): ");
	}
	
//	그룹 넘버가 유효하지 않음을 알리는 내용을 출력합니다.
	public void invalidGroup() {
		System.out.println("1~3 사이의 숫자를 입력해주세요.");
	}
	
// --------- 3. 여기까지 Insert 에서 쓰는 view 기능 ------------  //
	
	
// --------- 4. 여기부턴 DAO 에서 쓰는 view 기능 --------------- //

//	DB에 문제가 있음을 알리는 내용을 출력합니다. (SQLException)
	public void DBFail() {
		System.out.println("DB에 문제가 있습니다. "
						  + "\nDB를 확인해주세요.");
	}
	
//	DAO 에서 값이 정상적으로 DB에 들어가지 않았음을 출력합니다.
	public void rowcntFail() {
		System.out.println("DB에 문제가 있어 요청하신 기능이 정상작동되지 않았습니다."
						  + "\nDB를 확인해주세요.");
	}
	
}

