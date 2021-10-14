package com.vti.validation.Role;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.vti.repository.IRoleRepository;
/**
 * 
 * Represents a RoleCodeValidation
 *
 *
 * @author PTrXuan
 * Created on Sep 16, 2021
 */
public class RoleCodeValidation implements ConstraintValidator<RoleCodeExists, String> {

	@Autowired
	private IRoleRepository roleRepository;

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if (this.roleRepository.existsByRoleCode(value)) {
			return false;
		}
		return true;
	}

}
