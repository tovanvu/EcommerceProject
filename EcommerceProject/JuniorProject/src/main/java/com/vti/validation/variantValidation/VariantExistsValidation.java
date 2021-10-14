package com.vti.validation.variantValidation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import com.vti.repository.IGroupVariantRepository;

public class VariantExistsValidation implements ConstraintValidator<VariantExists, String> {
	@Autowired
	private IGroupVariantRepository repository;

	@Override
	public boolean isValid(String name, ConstraintValidatorContext context) {
		if (this.repository.existsByVariantName(name)) {
			return false;
		}
		return true;
	}
}
