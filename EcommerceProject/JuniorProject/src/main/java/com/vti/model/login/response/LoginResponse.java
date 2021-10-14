package com.vti.model.login.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vti.model.dto.response.UserResponse;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@AllArgsConstructor
public class LoginResponse {
	
	private UserResponse user;
	@JsonProperty("access_token")
	private String accessToken;
	@JsonProperty("expires_in")
	private int expireTime;
}