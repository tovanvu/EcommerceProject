package com.vti.validation.operator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.vti.ultils.MappingOperatorUtils;

public class operatorValidation implements ConstraintValidator<operator, String> {
	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if (MappingOperatorUtils.get(value) == null) {
			return false;
		}
		return true;
	}
}
