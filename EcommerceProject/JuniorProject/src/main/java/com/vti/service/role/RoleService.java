package com.vti.service.role;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;


import com.vti.config.specification.Role.RoleSpecificationBuilder;
import com.vti.exception.Message;
import com.vti.exception.NoDataFoundException;
import com.vti.model.dto.Role.Request.CreateRoleRequest;
import com.vti.model.dto.Role.Request.UpdateRoleRequest;
import com.vti.model.dto.Role.Response.PageRoleResponse;
import com.vti.model.dto.Role.Response.RoleResponse;
import com.vti.model.dto.Role.Response.RoleResponseDetail;
import com.vti.model.entity.Role;
import com.vti.model.entity.Users;
import com.vti.repository.IRoleRepository;
import com.vti.repository.IUserRepository;
/**
 * 
 * Represents a RoleService
 *
 *
 * @author PTrXuan
 * Created on Sep 16, 2021
 */
@Service
public class RoleService implements IRoleService {

	@Autowired
	private IRoleRepository roleRepository;

	@Autowired
	private IUserRepository userRepository;

	@Autowired
	private MessageSource messageSource;

	@Override
	public PageRoleResponse searchRole(Long id, String roleCode, String roleName, LocalDate createdFrom,
			LocalDate createdTo, LocalDate updatedFrom, LocalDate updatedTo, String emailUserCreate,
			String emailUserUpdate, int limit, int page) {

		Pageable pageable = PageRequest.of(page - 1, limit, Sort.by("id"));

		Specification<Role> where = RoleSpecificationBuilder.buildWhere(id, roleCode, roleName, createdFrom, createdTo,
				updatedFrom, updatedTo, emailUserCreate, emailUserUpdate);
		Page<Role> entityPages = roleRepository.findAll(where, pageable);
		List<Role> listRole = entityPages.getContent();
		List<RoleResponse> listRoleResponse = new ArrayList<>();
		for (Role role : listRole) {
			listRoleResponse.add(role.toRoleResponse());
		}
		if (createdFrom.isAfter(createdTo)) {
			throw new Message("Created from must less than created to");
		}
		if (updatedFrom.isAfter(updatedTo)) {
			throw new Message("Updated from must less than Updated to");
		}
		int pageCurrent = entityPages.getNumber();
		int totalPage = entityPages.getTotalPages();

		PageRoleResponse pageRoleResponse = new PageRoleResponse(listRoleResponse, (pageCurrent + 1), totalPage);

		return pageRoleResponse;
	}

	@Override
	public RoleResponseDetail getDetailRole(long id) {
		Role role = roleRepository.findById(id).orElse(null);
		if (role == null) {
			throw new NoDataFoundException(messageSource.getMessage("Get.role.id.notfound",
					new String[] { Long.toString(id) }, Locale.getDefault()));
		}
		RoleResponseDetail responseDetail = role.toRoleResponseDetail();

		return responseDetail;
	}

	@Override
	@Transactional
	public RoleResponse createRole(long userID, CreateRoleRequest form) {

		Users usersLogin = userRepository.findById(userID).orElse(null);
		if (usersLogin == null) {
			throw new NoDataFoundException(messageSource.getMessage("Get.user.id.notfound",
					new String[] { Long.toString(userID) }, Locale.getDefault()));
		}

		Role role = new Role();

		role.setName(form.getName());
		role.setRoleCode(form.getCode());
		role.setCreatedAt(LocalDate.now());
		role.setCreatedBy(usersLogin);

		role = roleRepository.save(role);

		RoleResponse roleResponse = role.toRoleResponse();

		return roleResponse;
	}

	@Transactional
	@Modifying
	@Override
	public RoleResponse updateRole(long userID, long roleID, UpdateRoleRequest form) {
		Users usersLogin = userRepository.findById(userID).orElse(null);
		if (usersLogin == null) {
			throw new NoDataFoundException(messageSource.getMessage("Get.user.id.notfound",
					new String[] { Long.toString(userID) }, Locale.getDefault()));
		}

		Role role = roleRepository.findById(roleID).orElse(null);

		if (role == null) {
			throw new NoDataFoundException(messageSource.getMessage("Get.role.id.notfound",
					new String[] { Long.toString(roleID) }, Locale.getDefault()));
		}

		role.setName(form.getName());

		role.setRoleCode(form.getCode());

		role.setUpdatedAt(LocalDate.now());
		role.setUpdatedBy(usersLogin);

		role = roleRepository.save(role);

		RoleResponse roleResponse = role.toRoleResponse();

		return roleResponse;
	}

	@Override
	public void deleteRole(long roleID) {

		Role role = roleRepository.findById(roleID).orElse(null);

		if (role == null) {
			throw new NoDataFoundException(messageSource.getMessage("Get.role.id.notfound",
					new String[] { Long.toString(roleID) }, Locale.getDefault()));
		}

		roleRepository.deleteById(roleID);
		;
	}

}
