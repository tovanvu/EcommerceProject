package com.vti.validation.productValidation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import com.vti.repository.IProductsRepository;

public class ProductValidation implements ConstraintValidator<ProductExists, Long> {
	@Autowired
	private IProductsRepository repository;

	@Override
	public boolean isValid(Long id, ConstraintValidatorContext context) {
		if (this.repository.existsById(id)) {
			return true;
		}
		return false;
	}
}
