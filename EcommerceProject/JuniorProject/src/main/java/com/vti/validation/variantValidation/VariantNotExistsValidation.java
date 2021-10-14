package com.vti.validation.variantValidation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import com.vti.repository.IGroupVariantRepository;

public class VariantNotExistsValidation implements ConstraintValidator<VariantNotExists, Long> {
	@Autowired
	private IGroupVariantRepository repository;

	@Override
	public boolean isValid(Long id, ConstraintValidatorContext context) {
		if (this.repository.existsById(id)) {
			return true;
		}
		return false;
	}
}
