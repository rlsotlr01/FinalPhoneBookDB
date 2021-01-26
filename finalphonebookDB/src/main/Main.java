package main;

import java.util.Scanner;

import controller.UserController;
import view.UserView;

/**
 * @작성자 : 김동윤
 * @작성일 : 2021. 1. 21.
 * @filename : Main.java
 * @package : main
 * @description : 데이터베이스를 연동한 전화번호부 프로그램을 실행하는 Main 코딩입니다.
 */
public class Main {

	public static void main(String[] args) {
//		유저 컨트롤러와 유저 뷰 내의 기능을 활용하기 위해서
//		두 클래스의 오브젝트를 각각 생성해줍니다.
		UserController uc = new UserController();
		UserView uv = new UserView();

//		1~5 중 하나의 숫자를 받아들이고,
//		그에 맞는 기능을 하도록 하는 반복문을 돌립니다.
//		(5를 선택하면 프로그램이 종료됩니다.)
		while (true) {
			uv.printMenu(); // 0. 메뉴를 출력한다.
			Scanner scanner = new Scanner(System.in);
			String choice = scanner.nextLine();
			if (choice.matches("1")) { // 1. 유저 추가 기능
				uc.insertUser(scanner);
			} else if (choice.matches("2")) { // 2. 모든 목록 보기 기능
				uc.selectAll();
			} else if (choice.matches("3")) { // 3. 수정 기능
				uc.updateUser(scanner);
			} else if (choice.matches("4")) { // 4. 삭제 기능
				uc.deleteUser(scanner);
			} else if (choice.matches("5")) { // 5. 종료 기능
				uv.printEnd();
				scanner.close();
				break;
			} else {
				System.out.println("1~5 사이의 숫자를 입력해주세요.");
			}

		}

	}
}
