package com.vti.validation.emailValidation;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import com.vti.repository.IUserRepository;

/**
 * email exists validate class
 * @author VuTV 
 * Created on 2021-09-14
 */
public class EmailValidation implements ConstraintValidator<EmailExists, String> {
	@Autowired
	private IUserRepository repository;

	@Override
	public boolean isValid(String email, ConstraintValidatorContext context) {
		if (this.repository.existsByEmail(email)) {
			return false;
		}
		return true;
	}
}
