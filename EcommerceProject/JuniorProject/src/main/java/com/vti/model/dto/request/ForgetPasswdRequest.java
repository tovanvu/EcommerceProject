package com.vti.model.dto.request;

import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vti.validation.passwordValid.ComfirmPasswdValid;
import com.vti.validation.passwordValid.ValidPassword;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Forget passwd request class
 * @author VuTV 
 * @Created_on 2021-09-17
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@ComfirmPasswdValid.List({
	@ComfirmPasswdValid(
			field = "newPasswd", 
			fieldMatch = "confirmPasswd")
})
public class ForgetPasswdRequest {

	@JsonProperty("new_passwd")
	@ValidPassword
	@NotBlank
	@Length(min = 8, max = 255)
	private String newPasswd;
	
	@JsonProperty("confirm_passwd")
	@ValidPassword
	@NotBlank
	@Length(min = 8, max = 255)
	private String confirmPasswd;
}
