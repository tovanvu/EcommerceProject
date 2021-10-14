package com.vti.model.dto.Role.Response;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * Represents a RoleResponse
 *
 *
 * @author PTrXuan Created on Sep 16, 2021
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleResponse {

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
	private User createdBy;

	@JsonProperty("updated_by")
	private User updatedBy;

	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	private class User {

		private Long id;

		private String name;
	}

	public RoleResponse(Long id, String roleCode, String name, LocalDate createdAt, LocalDate updatedAt,
			Long createdByID, String createdByName, Long updatedByID, String updatedByName) {
		super();
		this.id = id;
		this.roleCode = roleCode;
		this.name = name;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.createdBy = new User(createdByID, createdByName);
		this.updatedBy = new User(updatedByID, updatedByName);

	}

}
