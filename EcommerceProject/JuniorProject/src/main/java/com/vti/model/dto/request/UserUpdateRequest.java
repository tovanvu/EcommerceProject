package com.vti.model.dto.request;

import javax.validation.constraints.Email;
import org.springframework.lang.Nullable;
import com.vti.validation.emailValidation.EmailExists;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * User Update Request class
 * @author VuTV
 * Created on 2021-09-14
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserUpdateRequest {
	@Nullable
	@EmailExists
	@Email
	private String email;

	@Nullable
	private String name;
}