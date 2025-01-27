package com.smhrd.model;

public class MemberDTO {

	private String email; // 이메일
	private String pw; // 비밀번호
	private String name; // 이름
	private String nick; // 닉네임
	private String birthdate; // 생년월일
	private String gender; // 성별
	private String job; // 직업
	private String phone; // 전화번호
	private String userType; // 사용자 유형

	public MemberDTO(String email, String pw, String name, String nick, String birthdate, String gender, String job,
			String phone, String userType) {
		super();
		this.email = email;
		this.pw = pw;
		this.name = name;
		this.nick = nick;
		this.birthdate = birthdate;
		this.gender = gender;
		this.job = job;
		this.phone = phone;
		this.userType = userType;
	}

	public String getEmail() {
		return email;
	}

	public String getPw() {
		return pw;
	}

	public String getName() {
		return name;
	}

	public String getNick() {
		return nick;
	}

	public String getBirthdate() {
		return birthdate;
	}

	public String getGender() {
		return gender;
	}

	public String getJob() {
		return job;
	}

	public String getPhone() {
		return phone;
	}

	public String getUserType() {
		return userType;
	}
	
}
