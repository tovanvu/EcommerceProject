package com.vti.controller;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.bind.annotation.RestController;

import com.vti.exception.ValidationErrorException;
import com.vti.model.dto.ItemInCart;
import com.vti.model.dto.request.CreateItemFromCartRequest;
import com.vti.model.dto.response.OrdersRespone;
import com.vti.model.entity.Cart;
import com.vti.service.cart.ICartService;


@RestController
@RequestMapping(value = "api/v1")
@CrossOrigin("*")
public class CartController {
	@Autowired
	private ICartService service;

	@SuppressWarnings("serial")
	@PostMapping(value = "/cart")
	public ResponseEntity<?> GenCart() {
		Cart cart = service.GenerateCartID();
		HashMap<String, String> response = new HashMap<String, String>() {
			{
				put("cart_id", cart.getId());
			}
		};
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping(value = "/cart/{id}")
	public ResponseEntity<?> GetItemForCart(@PathVariable(name = "id") String id) {
		List<ItemInCart> listItemInCarts = service.GetIteamForCart(id);
		return new ResponseEntity<>(listItemInCarts, HttpStatus.OK);
	}

	@PostMapping(value = "/cart/{id}")
	public ResponseEntity<?> AddItemToCart(@PathVariable(name = "id") String id,
			@RequestBody @Validated ItemInCart itemInCart, Errors errors) {
		if (errors.hasErrors()) {
			throw new ValidationErrorException(errors);
		}
		service.AddItemToCart(id, itemInCart);
		return new ResponseEntity<>("Add item success", HttpStatus.OK);
	}

	@DeleteMapping(value = "/cart/{id}/{product_id}")
	public ResponseEntity<?> remoteItemInCart(@PathVariable(name = "id") String id,
			@PathVariable(name = "product_id") long productID) {
		service.RemoveCart(id, productID);
		return new ResponseEntity<>("Remove item success", HttpStatus.OK);
	}

	@PutMapping(value = "/cart/{id}/quantity")
	public ResponseEntity<?> UpdateCart(@PathVariable(name = "id") String id,
			@RequestBody @Validated ItemInCart itemInCart, Errors errors) {
		if (errors.hasErrors()) {
			throw new ValidationErrorException(errors);
		}
		service.UpdateCart(id, itemInCart);
		return new ResponseEntity<>("Update quantity success", HttpStatus.OK);
	}

	@PostMapping(value = "/cart/{id}/order")
	public ResponseEntity<?> CreateItemFromCart(@PathVariable(name = "id") String id,
			@RequestBody @Validated CreateItemFromCartRequest orders, Errors errors) {
		if (errors.hasErrors()) {
			throw new ValidationErrorException(errors);
		}
		OrdersRespone respone = service.CreateItemForCart(id, orders);
		return new ResponseEntity<>(respone, HttpStatus.OK);
	}
}