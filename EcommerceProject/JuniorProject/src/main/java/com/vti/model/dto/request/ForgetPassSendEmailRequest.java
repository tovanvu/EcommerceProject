/**
 * 
 * @author PTrXuan
 * Created on Sep 23, 2021
 */
package com.vti.model.dto.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a ForgetPassSendEmailRequest
 *
 *
 * @author PTrXuan
 * Created on Sep 23, 2021
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ForgetPassSendEmailRequest {

	@NotBlank
	@Email
	private String email;
}
