package com.vti.model.login.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

import com.vti.validation.emailValidation.EmailNotExist;

import lombok.Data;
import lombok.NoArgsConstructor;
@NoArgsConstructor
@Data
public class LoginRequest {
	@NotBlank
	@Length(min = 6, max = 50)
	@Email
	@EmailNotExist
	private String email;
	
	@NotBlank
	@Length(min = 8, max = 50)
	private String passwd;
}