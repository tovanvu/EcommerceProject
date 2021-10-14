package com.vti.validation.group;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import com.vti.repository.IProductsGroupRepository;
/**
 * 
 * Represents a GroupExistsValidation
 *
 *
 * @author VuTV
 * Created on Sep 26, 2021
 */
public class GroupExistsValidation implements ConstraintValidator<GroupExists, String> {

	@Autowired
	private IProductsGroupRepository groupRepository;

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if (this.groupRepository.existsByName(value)) {
			return false;
		}
		return true;
	}

}
