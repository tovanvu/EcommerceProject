package com.vti.controller;

import java.io.IOException;
import java.security.Principal;
import java.time.LocalDate;

import javax.mail.MessagingException;
import javax.validation.constraints.Email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vti.exception.ValidationErrorException;
import com.vti.model.dto.request.ChangePasswdRequest;
import com.vti.model.dto.request.ForgetPassSendEmailRequest;
import com.vti.model.dto.request.ForgetPasswdRequest;
import com.vti.model.dto.request.RegisterUserRequest;
import com.vti.model.dto.request.UpdateRoleUserRequest;
import com.vti.model.dto.request.UserUpdateRequest;
import com.vti.model.dto.response.SearchUserResponse;
import com.vti.model.dto.response.UserResponse;
import com.vti.service.users.IUserService;

/**
 * User API controller
 * 
 * @author VuTV
 * @Created_on 2021-09-14
 */
@CrossOrigin("*")
@RestController
@RequestMapping(value = "api/v1")
@Validated
public class UserController extends BaseAbtractController {

	@Autowired
	private IUserService service;

	@GetMapping(value = "/users")
	@PreAuthorize("hasRole('AD')")
	public ResponseEntity<?> searchUsers(@RequestParam(name = "name", required = false) String name,
			@RequestParam(name = "role_code", required = false) String roleCode,
			@RequestParam(name = "rank_name", required = false) String rankName,
			@RequestParam(name = "id", required = false) Long id,
			@RequestParam(name = "email", required = false) @Email String email,
			@RequestParam(name = "created_from", required = false, defaultValue = "1970-01-01") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate createdFrom,
			@RequestParam(name = "created_to", required = false, defaultValue = "9999-12-30") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate createdTo,
			Pageable pageable, Principal principal) {
//		String roleUser = getCustomerRole(principal);
//		if (!roleUser.equals("admin")) {
//			throw new Message("User is not Admin");
//		}
		SearchUserResponse searchRespone = service.searchUser(id, name, email, roleCode, rankName, createdFrom,
				createdTo, pageable);
		return new ResponseEntity<>(searchRespone, HttpStatus.OK);
	}

	@GetMapping(value = "/users/{id}")
	@PreAuthorize("hasRole('AD')")
	public ResponseEntity<?> userDetail(@PathVariable(name = "id") long id, Principal principal) {
		UserResponse response = new UserResponse();
//		String roleUser = getCustomerRole(principal);
//		Long userLoginId = getCustomerId(principal);
//		if (!roleUser.equals("admin") && userLoginId.equals(id) || roleUser.equals("admin")) {
		response = service.userDetail(id);
//		} else {
//			throw new Message("User is not Admin");
//		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PostMapping(value = "/register")
	public ResponseEntity<?> registerUser(@RequestBody @Validated RegisterUserRequest register, Errors errors) {
		if (errors.hasErrors()) {
			throw new ValidationErrorException(errors);
		}
		UserResponse response = service.registerUser(register);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PutMapping(value = "/users")
	public ResponseEntity<?> updateUser(@RequestBody @Validated UserUpdateRequest request, Errors errors,
			Principal principal) {
		if (errors.hasErrors()) {
			throw new ValidationErrorException(errors);
		}
		long userID = getCustomerId(principal);
		UserResponse response = service.updateUser(userID, request);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PutMapping(value = "/users/{id}")
	@PreAuthorize("hasRole('AD')")
	public ResponseEntity<?> updateRoleCodeUser(@PathVariable(name = "id") long id,
			@RequestBody @Validated UpdateRoleUserRequest request, Errors errors, Principal principal) {
		if (errors.hasErrors()) {
			throw new ValidationErrorException(errors);
		}
//		String roleUser = getCustomerRole(principal);
//		if (!roleUser.equals("admin")) {
//			throw new Message("User is not Admin");
//		}
		UserResponse response = service.updateRoleUser(id, request);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping(value = "/users/current")
	public ResponseEntity<?> currentUser(Principal principal) {
		Long userLoginId = getCustomerId(principal);
		UserResponse response = service.currentUser(userLoginId);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PutMapping(value = "/users/password-change")
	public ResponseEntity<?> changePasswd(@RequestBody @Validated ChangePasswdRequest request, Errors errors,
			Principal principal) {
		if (errors.hasErrors()) {
			throw new ValidationErrorException(errors);
		}
		long userID = getCustomerId(principal);
		UserResponse response = service.changePasswd(userID, request);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PostMapping(value = "/password_reset")
	public ResponseEntity<?> forgetPassword(@RequestBody @Validated ForgetPassSendEmailRequest emailrequest,
			Errors errors) throws MessagingException {
		if (errors.hasErrors()) {
			throw new ValidationErrorException(errors);
		}
		service.forgetPassword(emailrequest);

		return new ResponseEntity<>("We have sent an email. Please check email to reset password!", HttpStatus.OK);
	}

	@PostMapping(value = "/password_reset/{token}")
	public ResponseEntity<?> resetPassword(@PathVariable(name = "token") String token,
			@RequestBody @Validated ForgetPasswdRequest request, Errors errors) throws MessagingException, IOException {
		if (errors.hasErrors()) {
			throw new ValidationErrorException(errors);
		}
		service.resetPassword(token, request);
		return new ResponseEntity<>("Change passwd success, check your mail again", HttpStatus.OK);
	}

}
