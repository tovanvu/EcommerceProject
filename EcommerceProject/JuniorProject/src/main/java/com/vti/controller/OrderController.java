package com.vti.controller;

import java.util.List;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.vti.model.dto.request.OrderRequest;
import com.vti.model.dto.request.OrderUpdateRequest;
import com.vti.model.dto.response.OrdersRespone;
import com.vti.service.orders.IOrdersService;
import com.vti.exception.ValidationErrorException;
import com.vti.validation.operator.operator;

@RestController
@RequestMapping(value = "api/v1")
@CrossOrigin("*")
@Validated
public class OrderController {
	@Autowired
	private IOrdersService service;

	@GetMapping(value = "/orders")
	public ResponseEntity<?> SearchOrders(Pageable pageable, 
			@RequestParam(name = "name", required = false) String name,
			@RequestParam(name = "variant_name", required = false) String variant_name,
			@RequestParam(name = "product_name", required = false) String product_name,		
			@RequestParam(name = "address", required = false) String address,
			@RequestParam(name = "total_price", required = false) Long total_price,
			@operator @RequestParam(name = "operator", required = false, defaultValue = "eq") String operator,
			@RequestParam(name = "id", required = false) Long id) {
		List<OrdersRespone> listOrdersRespones = service.searchOrders(pageable, name, variant_name, product_name, id,
				address, total_price, operator);
		return new ResponseEntity<>(listOrdersRespones, HttpStatus.OK);
	}

	@PostMapping(value = "/order")
	public ResponseEntity<?> AddOrders(@RequestBody @Validated OrderRequest orderRequest, Errors errors) {
		if (errors.hasErrors()) {
			throw new ValidationErrorException(errors);
		}
		OrdersRespone respones = service.AddOrder(orderRequest);
		return new ResponseEntity<>(respones, HttpStatus.OK);
	}

	@GetMapping(value = "/order/{id}")
	public ResponseEntity<?> OrderDetail(@PathVariable(name = "id") long id) {
		OrdersRespone respone = service.OrderDetail(id);
		return new ResponseEntity<>(respone, HttpStatus.OK);
	}

	@PutMapping(value = "/order/{id}")
	public ResponseEntity<?> UpdateOrder(@PathVariable(name = "id") Long id,
			@RequestBody @Validated OrderUpdateRequest orderRequest, Errors errors) {
		if (errors.hasErrors()) {
			throw new ValidationErrorException(errors);
		}
		OrdersRespone respone = service.UpdateOrder(id, orderRequest);
		return new ResponseEntity<>(respone, HttpStatus.OK);
	}
}
