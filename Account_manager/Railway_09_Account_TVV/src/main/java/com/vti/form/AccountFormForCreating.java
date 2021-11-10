package com.vti.form;

public class AccountFormForCreating {
	private String email;
	private String username;
	private String fullname;
	private String passWord;
	private short departmentId;
	private short positionId;

	public AccountFormForCreating() {
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUsername() {
		return username;
	}

	public String getPassWord() {
		return passWord;
	}

	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public short getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(short departmentId) {
		this.departmentId = departmentId;
	}

	public short getPositionId() {
		return positionId;
	}

	public void setPositionId(short positionId) {
		this.positionId = positionId;
	}
}
