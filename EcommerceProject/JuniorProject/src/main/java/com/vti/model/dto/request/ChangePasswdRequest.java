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
 * change passwd request class
 * @author VuTV 
 * Created on 2021-09-15
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@ComfirmPasswdValid.List({
	@ComfirmPasswdValid(
			field = "newPassWd", 
			fieldMatch = "confirmPasswd")
})
public class ChangePasswdRequest {
	@JsonProperty("current_passwd")
	@NotBlank
	@Length(min = 8, max = 255)
	private String currentPassWd;
	
	@JsonProperty("new_passwd")
	@ValidPassword
	@NotBlank
	@Length(min = 8, max = 255)
	private String newPassWd;
	
	@JsonProperty("confirm_passwd")
	@ValidPassword
	@NotBlank
	@Length(min = 8, max = 255)
	private String confirmPasswd;

}
