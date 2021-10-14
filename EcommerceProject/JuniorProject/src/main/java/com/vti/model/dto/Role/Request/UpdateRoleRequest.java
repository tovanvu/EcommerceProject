package com.vti.model.dto.Role.Request;

import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

import com.vti.validation.Role.RoleCodeExists;
import com.vti.validation.Role.RoleNameExists;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
/**
 * 
 * Represents a UpdateRoleRequest
 *
 *
 * @author PTrXuan
 * Created on Sep 16, 2021
 */
public class UpdateRoleRequest {

	@Length(max = 256)
	@NotBlank
	@RoleCodeExists
	private String code;
	
	@Length(max = 256)
	@NotBlank
	@RoleNameExists
	private String name;
	
}
