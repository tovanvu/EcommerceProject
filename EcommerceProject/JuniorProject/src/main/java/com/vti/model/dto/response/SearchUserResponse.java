package com.vti.model.dto.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class SearchUserResponse {
	private List<UserResponse> list;

	private int total;

	private int page;
}
