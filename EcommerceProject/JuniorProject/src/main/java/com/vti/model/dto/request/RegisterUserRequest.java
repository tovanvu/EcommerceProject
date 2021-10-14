package com.vti.model.dto.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vti.validation.emailValidation.EmailExists;
import com.vti.validation.passwordValid.ComfirmPasswdValid;
import com.vti.validation.passwordValid.ValidPassword;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Register User Request class
 * @author VuTV
 * Created on 2021-09-14
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@ComfirmPasswdValid.List({
	@ComfirmPasswdValid(
			field = "passwd", 
			fieldMatch = "comfirmPasswd")
})
public class RegisterUserRequest {
	@NotBlank
	@EmailExists
	@Email
	private String email;

	@NotBlank
	private String name;

	@ValidPassword
	@NotBlank
	@Length(min = 8, max = 255)
	private String passwd;

	@ValidPassword
	@NotBlank
	@Length(min = 8, max = 255)
	@JsonProperty("comfirm_passwd")
	private String comfirmPasswd;

}
