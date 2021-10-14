package com.vti.controller;

import java.security.Principal;
import java.time.LocalDate;

import javax.validation.constraints.Email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vti.exception.Message;
import com.vti.exception.ValidationErrorException;
import com.vti.model.dto.Role.Request.CreateRoleRequest;
import com.vti.model.dto.Role.Request.UpdateRoleRequest;
import com.vti.model.dto.Role.Response.PageRoleResponse;
import com.vti.model.dto.Role.Response.RoleResponse;
import com.vti.model.dto.Role.Response.RoleResponseDetail;
import com.vti.service.role.IRoleService;
/**
 * 
 * Represents a RoleController
 *
 *
 * @author PTrXuan
 * Created on Sep 16, 2021
 */
@RestController
@RequestMapping(value = "/api/v1")
@CrossOrigin("*")
@Validated
public class RoleController extends BaseAbtractController {

	@Autowired
	private IRoleService roleService;

	@GetMapping(value = "/roles")
	public ResponseEntity<?> searchRole(@RequestParam(required = false) Long id,
			@RequestParam(name = "role_code", required = false) String roleCode,
			@RequestParam(name = "role_name", required = false) String roleName,
			@RequestParam(name = "created_from", required = false, defaultValue = "1970-01-01") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate createdFrom,
			@RequestParam(name = "created_to", required = false, defaultValue = "9999-12-30") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate createdTo,
			@RequestParam(name = "updated_from", required = false, defaultValue = "1970-01-01") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate updatedFrom,
			@RequestParam(name = "updated_to", required = false, defaultValue = "9999-12-30") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate updatedTo,
			@RequestParam(name = "email_user_create", required = false) @Email String emailUserCreate,
			@RequestParam(name = "email_user_update", required = false) @Email String emailUserUpdate,
			@RequestParam(name = "limit", required = false, defaultValue = "10") int limit,
			@RequestParam(name = "page", required = false, defaultValue = "1") int page, Principal principal) {

		String rolerUser = getCustomerRole(principal);
		if (!rolerUser.equals("admin")) {
			throw new Message("User is not admin");
		}

		PageRoleResponse pageRole = roleService.searchRole(id, roleCode, roleName, createdFrom, createdTo, updatedFrom,
				updatedTo, emailUserCreate, emailUserUpdate, limit, page);

		return new ResponseEntity<>(pageRole, HttpStatus.OK);

	}

	@GetMapping(value = "/roles/{id}")
	public ResponseEntity<?> getDetailRole(@PathVariable(name = "id") long id, Principal principal) {

		String rolerUser = getCustomerRole(principal);
		if (!rolerUser.equals("admin")) {
			throw new Message("User is not admin");
		}

		RoleResponseDetail responseDetail = roleService.getDetailRole(id);

		return new ResponseEntity<>(responseDetail, HttpStatus.OK);

	}

	@PostMapping(value = "/roles")
	public ResponseEntity<?> createRole(@RequestBody @Validated CreateRoleRequest form, Errors errors,
			Principal principal) {
		if (errors.hasErrors()) {
			throw new ValidationErrorException(errors);
		}

		String rolerUser = getCustomerRole(principal);
		if (!rolerUser.equals("admin")) {
			throw new Message("User is not admin");
		}

		long userID = getCustomerId(principal);

		RoleResponse roleResponse = roleService.createRole(userID, form);

		return new ResponseEntity<>(roleResponse, HttpStatus.CREATED);

	}

	@PutMapping(value = "/roles/{id}")
	public ResponseEntity<?> updateRole(@PathVariable(name = "id") long roleID,
			@RequestBody @Validated UpdateRoleRequest form, Errors errors, Principal principal) {
		if (errors.hasErrors()) {
			throw new ValidationErrorException(errors);
		}

		String rolerUser = getCustomerRole(principal);
		if (!rolerUser.equals("admin")) {
			throw new Message("User is not admin");
		}

		long userID = getCustomerId(principal);

		RoleResponse roleResponse = roleService.updateRole(userID, roleID, form);

		return new ResponseEntity<>(roleResponse, HttpStatus.CREATED);

	}

	@DeleteMapping(value = "/roles/{id}")
	public ResponseEntity<?> deleteRole(@PathVariable(name = "id") long roleID, Principal principal) {

		String rolerUser = getCustomerRole(principal);
		if (!rolerUser.equals("admin")) {
			throw new Message("User is not admin");
		}

		roleService.deleteRole(roleID);

		return new ResponseEntity<>("Delete Success!!!", HttpStatus.CREATED);

	}

}
