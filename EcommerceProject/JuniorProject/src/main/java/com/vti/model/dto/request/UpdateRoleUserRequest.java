package com.vti.model.dto.request;

import org.springframework.lang.Nullable;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Update Role code of User Request class
 * @author VuTV
 * Created on 2021-09-15
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UpdateRoleUserRequest {
	@Nullable
	@JsonProperty("code")
	private String roleCode;
}
