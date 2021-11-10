package com.vti.form;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AccountFormForCreatingRegister {
	private String email;
	private String username;
	private String fullname;
	private short departmentId;
	private short positionId;
	private String password;

}
