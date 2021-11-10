package com.vti.dto;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.vti.entity.AccountRoles;
import com.vti.entity.AccountStatus;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Setter
@Getter
@ToString
public class LoginInfoDto {
	private short id;
	
	private String fullName;
	
	@Enumerated(EnumType.ORDINAL)
	private AccountStatus status = AccountStatus.NOT_ACTIVE;
	
	@Enumerated(EnumType.STRING)
	private AccountRoles role = AccountRoles.USER;

	public LoginInfoDto(short id, String fullName, AccountStatus status, AccountRoles role) {
		super();
		this.id = id;
		this.fullName = fullName;
		this.status = status;
		this.role = role;
	}	
	
}