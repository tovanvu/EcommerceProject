package com.vti.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class AccountDto {
	private short id;
	private String email;
	private String username;
	private String fullname;
	private String department;
	private String position;
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date createDate;

	public AccountDto(short id, String email, String username, String fullname, String department, String position,
			Date createDate) {
		super();
		this.id = id;
		this.email = email;
		this.username = username;
		this.fullname = fullname;
		this.department = department;
		this.position = position;
		this.createDate = createDate;
	}

	public short getId() {
		return id;
	}

	public String getEmail() {
		return email;
	}

	public String getUsername() {
		return username;
	}

	public String getFullname() {
		return fullname;
	}

	public String getDepartment() {
		return department;
	}

	public String getPosition() {
		return position;
	}

	public Date getCreateDate() {
		return createDate;
	}

}
