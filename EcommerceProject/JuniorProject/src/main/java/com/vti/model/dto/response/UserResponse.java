package com.vti.model.dto.response;

import com.vti.model.dto.RankDTO;
import com.vti.model.dto.RoleDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * User Response class
 * @author VuTV 
 * Created on 2021-09-14
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserResponse {
	private long id;
	
	private String name;

	private String email;

	private RoleDTO role;

	private RankDTO rank;

	public UserResponse(long id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
}
