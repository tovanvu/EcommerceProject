package com.vti.model.dto.Role.Response;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vti.model.dto.response.UserResponse;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * 
 * Represents a RoleResponseDetail
 *
 *
 * @author PTrXuan
 * Created on Sep 16, 2021
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleResponseDetail {

	private Long id;

	@JsonProperty("role_code")
	private String roleCode;

	@JsonProperty("role_name")
	private String name;

	@JsonProperty("created_at")
	private LocalDate createdAt;

	@JsonProperty("updated_at")
	private LocalDate updatedAt;

	@JsonProperty("created_by")
	private UserResponse createdBy;

	@JsonProperty("updated_by")
	private UserResponse updatedBy;

}
