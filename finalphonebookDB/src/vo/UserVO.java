package vo;

/**
 * @작성자 : 김동윤
 * @작성일 : 2021. 1. 21.
 * @filename : UserVO.java
 * @package : vo
 * @description : 연락처 하나하나의 개인정보를 담는 객체 클래스 입니다.
 */
public class UserVO {
//	유저의 이름을 담습니다.
	private String name;
//	유저의 휴대폰 번호를 담습니다.
	private String phoneNumber;
//	유저의 주소를 담습니다.
	private String address;
//	유저가 속한 그룹의 그룹번호를 담습니다.
	private String group1;

//	유저의 이름을 불러오는 게터함수입니다.
	public String getName() {
		return name;
	}

//	유저의 이름을 지정하는 세터함수입니다.
	public void setName(String name) {
		this.name = name;
	}
	
//	유저의 휴대폰 번호를 불러오는 게터함수입니다.
	public String getPhoneNumber() {
		return phoneNumber;
	}

//	유저의 핸드폰 번호을 지정하는 세터함수입니다.
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

//	유저의 주소를 불러오는 게터함수입니다.
	public String getAddress() {
		return address;
	}

//	유저의 주소를 지정하는 세터함수입니다.
	public void setAddress(String address) {
		this.address = address;
	}

//	유저가 속한 그룹의 번호를 불러오는 게터함수입니다.
	public String getGroup() {
		return group1;
	}

//	유저가 속한 그룹의 번호를 지정하는 세터함수입니다.
	public void setGroup(String group) {
		this.group1 = group;
	}

//	편의를 위해서 유저 객체 안의 정보들을 한 줄의 String 으로 반환해주도록 오버라이딩 해줍니다.
	@Override
	public String toString() {
		return "이름 : " + name + ", 전화번호 : " + phoneNumber + ", 주소 : " + address + ", 종류 : " + group1;
	}

}
