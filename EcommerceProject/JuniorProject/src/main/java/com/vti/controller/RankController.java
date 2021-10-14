/**
 * 
 */
package com.vti.controller;

import java.security.Principal;
import java.time.LocalDate;

import javax.validation.Valid;
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
import com.vti.model.dto.request.RankRequest;
import com.vti.model.dto.response.RankResponse;
import com.vti.model.dto.response.RankResponseDetail;
import com.vti.model.dto.response.RankResponsePageable;
import com.vti.repository.IUserRepository;
import com.vti.service.rank.IRankService;
import com.vti.service.rank.RankQueryService;

/**
 * This class is RankController
 * 
 * @Description: ...
 * @author: KienTT
 * @create_date: Sep 14, 2021
 * @version: 1.0
 * @modifer: KienTT
 * @modifer_date: Sep 14, 2021
 */

@RestController
@RequestMapping(value = "api/v1/ranks")
@CrossOrigin("*")
@Validated
public class RankController extends BaseAbtractController {
	@Autowired
	RankQueryService rankQueryService;

	@Autowired
	IRankService rankService;

	@Autowired
	IUserRepository userRepository;

	@GetMapping()
	public ResponseEntity<?> searchRank(@RequestParam(required = false) Long id,
			@RequestParam(required = false) String rank_name,
			@RequestParam(name = "created_from", required = false, defaultValue = "1970-01-01") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate createdFrom,
			@RequestParam(name = "created_to", required = false, defaultValue = "9999-12-30") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate createdTo,
			@RequestParam(name = "updated_from", required = false, defaultValue = "1970-01-01") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate updatedFrom,
			@RequestParam(name = "updated_to", required = false, defaultValue = "9999-12-30") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate updatedTo,
			@RequestParam(name = "email_user_create", required = false) @Email String emailUserCreated,
			@RequestParam(name = "email_user_update", required = false) @Email String emailUserUpdated,
			@RequestParam(required = false, defaultValue = "1") int page,
			@RequestParam(required = false, defaultValue = "10") int limit, Principal principal) {

		String roleUser = getCustomerRole(principal);
		if (!roleUser.equals("admin")) {
			throw new Message("User is not Admin");
		}
		RankResponsePageable rankResponsePageable = rankQueryService.search(id, rank_name, createdFrom, createdTo,
				updatedFrom, updatedTo, emailUserCreated, emailUserUpdated, page, limit);

		return new ResponseEntity<>(rankResponsePageable, HttpStatus.OK);

	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<?> getById(@PathVariable(name = "id", required = true) Long id, Principal principal) {
		String roleUser = getCustomerRole(principal);
		if (!roleUser.equals("admin")) {
			throw new Message("User is not Admin");
		}
		RankResponseDetail rankResponseDetail = rankService.getById(id);

		return new ResponseEntity<RankResponseDetail>(rankResponseDetail, HttpStatus.OK);
	}

	@PostMapping("")
	public ResponseEntity<?> createRank(@Valid @RequestBody RankRequest request, Errors errors, Principal principal) {

		String roleUser = getCustomerRole(principal);
		if (!roleUser.equals("admin")) {
			throw new Message("User is not Admin");
		}

		if (errors.hasErrors()) {
			throw new ValidationErrorException(errors);
		}
		RankResponse response = rankService.createRank(request);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> updateRank(@PathVariable(name = "id") Long id, @Valid @RequestBody RankRequest request,
			Errors errors, Principal principal) {
		String roleUser = getCustomerRole(principal);
		if (!roleUser.equals("admin")) {
			throw new Message("User is not Admin");
		}

		if (errors.hasErrors()) {
			throw new ValidationErrorException(errors);
		}

		RankResponse response = rankService.updateRank(id, request);
		return new ResponseEntity<>(response, HttpStatus.OK);

	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<?> deleteRank(@PathVariable(name = "id") Long id, Principal principal) {
		String roleUser = getCustomerRole(principal);
		if (!roleUser.equals("admin")) {
			throw new Message("User is not Admin");
		}
		rankService.deleteRank(id);
		return new ResponseEntity<String>("Remove access!", HttpStatus.OK);
	}

}
