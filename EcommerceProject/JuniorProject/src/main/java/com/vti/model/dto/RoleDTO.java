package com.vti.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RoleDTO {
	private Long id;

	@JsonProperty("role_code")
	private String roleCode;

	@JsonProperty("role_name")
	private String roleName;
}
