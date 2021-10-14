package com.vti.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vti.model.dto.request.GroupUpdateRequest;
import com.vti.model.dto.request.ProductGroupsRequest;
import com.vti.model.dto.response.ProductGroupRespone;
import com.vti.model.dto.response.SearchGroupResponse;
import com.vti.service.productsGroup.IProductsGroupService;
import com.vti.exception.ValidationErrorException;

@RestController
@RequestMapping
@CrossOrigin("*")
public class ProductGroupController {
	@Autowired
	private IProductsGroupService service;

	@GetMapping(value = "api/v1/groups")
	public ResponseEntity<?> getAll(Pageable pageable) {
		SearchGroupResponse listRespones = service.GetAllGroup(pageable);
		return new ResponseEntity<>(listRespones, HttpStatus.OK);
	}

	@PostMapping(value = "api/v1/group")
	public ResponseEntity<?> AddGroup(@RequestBody @Validated ProductGroupsRequest productGroupsRequest,
			Errors errors) {
		if (errors.hasErrors()) {
			throw new ValidationErrorException(errors);
		}
		ProductGroupRespone respones = service.AddGroup(productGroupsRequest);
		return new ResponseEntity<>(respones, HttpStatus.OK);
	}

	@GetMapping(value = "api/v1/group/{id}")
	public ResponseEntity<?> GroupDetail(@PathVariable(name = "id", required = true) Long id) {
		ProductGroupRespone respone = service.GetGroupById(id);
		return new ResponseEntity<>(respone, HttpStatus.OK);
	}

	@PutMapping(value = "api/v1/group/{id}")
	public ResponseEntity<?> UpdateGroup(@PathVariable(name = "id") Long id,
			@RequestBody @Validated GroupUpdateRequest productGroupsRequest, Errors errors) {
		if (errors.hasErrors()) {
			throw new ValidationErrorException(errors);
		}
		ProductGroupRespone respones = service.UpdateGroup(id, productGroupsRequest);
		return new ResponseEntity<>(respones, HttpStatus.OK);
	}
}
