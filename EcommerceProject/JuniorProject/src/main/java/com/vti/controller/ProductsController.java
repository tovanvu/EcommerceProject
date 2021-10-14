package com.vti.controller;

import java.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.vti.model.dto.request.ProductUpdateRequest;
import com.vti.model.dto.request.ProductsRequest;
import com.vti.model.dto.response.ProductsRespone;
import com.vti.model.dto.response.SearchProductRespone;
import com.vti.service.products.IProductsService;
import com.vti.exception.ValidationErrorException;

@RestController
@RequestMapping
@CrossOrigin("*")
@Validated
public class ProductsController {
	@Autowired
	private IProductsService service;

	@GetMapping(value = "api/v1/products")
	public ResponseEntity<?> Search(
			@RequestParam(name = "name", required = false) String name,
			@RequestParam(name = "group_name", required = false) String group_name,
			@RequestParam(name = "id", required = false) Long id, 
			@DateTimeFormat(pattern = "yyyy-MM-dd") @RequestParam(name="created_from", required = false, 
			defaultValue = "1970-01-01") LocalDate createdFrom,
			@DateTimeFormat(pattern = "yyyy-MM-dd") @RequestParam(name="created_to", required = false,
            defaultValue = "9999-12-30") LocalDate createdTo, Pageable pageable) {
		SearchProductRespone searchRespone = service.getAll(pageable, name, createdFrom ,createdTo, group_name, id);
		return new ResponseEntity<>(searchRespone, HttpStatus.OK);
	}

	@PostMapping(value = "api/v1/product")
	public ResponseEntity<?> AddProducts(@RequestBody @Validated ProductsRequest addProducts, Errors errors) {
		if (errors.hasErrors()) {
			throw new ValidationErrorException(errors);
		}
		ProductsRespone dto = service.AddProducts(addProducts);
		return new ResponseEntity<>(dto, HttpStatus.OK);
	}

	@GetMapping(value = "api/v1/product/{id}")
	public ResponseEntity<?> ProductsDetail(@PathVariable(name = "id") Long id) {
		ProductsRespone dto = service.GetProductsById(id);
		return new ResponseEntity<>(dto, HttpStatus.OK);
	}

	@PutMapping(value = "api/v1/product/{id}")
	public ResponseEntity<?> updateProducts(@PathVariable(name = "id") Long id,
			@RequestBody @Validated ProductUpdateRequest productsRequest, Errors errors) {
		if (errors.hasErrors()) {
			throw new ValidationErrorException(errors);
		}
		ProductsRespone dto = service.updateProduct(id, productsRequest);
		return new ResponseEntity<>(dto, HttpStatus.OK);
	}

}
