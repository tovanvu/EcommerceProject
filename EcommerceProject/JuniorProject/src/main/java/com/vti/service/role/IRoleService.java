package com.vti.service.role;

import java.time.LocalDate;

import com.vti.model.dto.Role.Request.CreateRoleRequest;
import com.vti.model.dto.Role.Request.UpdateRoleRequest;
import com.vti.model.dto.Role.Response.PageRoleResponse;
import com.vti.model.dto.Role.Response.RoleResponse;
import com.vti.model.dto.Role.Response.RoleResponseDetail;
/**
 * 
 * Represents a IRoleService
 *
 *
 * @author PTrXuan
 * Created on Sep 16, 2021
 */
public interface IRoleService {

	public PageRoleResponse searchRole(Long id, String roleCode, String roleName, LocalDate createdFrom,
			LocalDate createdTo, LocalDate updatedFrom, LocalDate updatedTo, String emailUserCreate,
			String emailUserUpdate, int limit, int page);
	
	public RoleResponseDetail getDetailRole(long id);

	public RoleResponse createRole(long userID,CreateRoleRequest form);

	public RoleResponse updateRole(long userID,long roleID , UpdateRoleRequest form);

	public void deleteRole(long roleID);
	
}
