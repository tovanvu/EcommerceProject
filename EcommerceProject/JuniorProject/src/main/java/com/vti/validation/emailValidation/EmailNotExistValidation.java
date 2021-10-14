/**
 * 
 * @author PTrXuan
 * Created on Sep 25, 2021
 */
package com.vti.validation.emailValidation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.vti.repository.IUserRepository;

/**
 * Represents a EmailNotExistValidation
 *
 *
 * @author PTrXuan
 * Created on Sep 25, 2021
 */
public class EmailNotExistValidation implements ConstraintValidator<EmailNotExist, String>{
	
	@Autowired
	private IUserRepository repository;

	@Override
	public boolean isValid(String email, ConstraintValidatorContext context) {
		if (this.repository.existsByEmail(email)) {
			return true;
		}
		return false;
	}

}
