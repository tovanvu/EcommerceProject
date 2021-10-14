package com.vti.validation.Role;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.vti.repository.IRoleRepository;
/**
 * 
 * Represents a RoleNameValidation
 *
 *
 * @author PTrXuan
 * Created on Sep 16, 2021
 */
public class RoleNameValidation implements ConstraintValidator<RoleNameExists, String> {

	@Autowired
	private IRoleRepository roleRepository;

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if (this.roleRepository.existsByName(value)) {
			return false;
		}
		return true;
	}

}
