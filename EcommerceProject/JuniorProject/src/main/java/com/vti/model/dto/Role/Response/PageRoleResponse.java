package com.vti.model.dto.Role.Response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * 
 * Represents a PageRoleResponse
 *
 *
 * @author PTrXuan
 * Created on Sep 16, 2021
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class PageRoleResponse {

	private List<RoleResponse> list;

	private int page;

	private int total;
}
