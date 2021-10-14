/**
 * 
 */
package com.vti.validation.rank;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.vti.repository.IRankRepository;

/**
 * This class is CheckRankExist
 * 
 * @Description: check rank name exist
 * @author: KienTT
 * @create_date: Sep 15, 2021
 * @version: 1.0
 * @modifer: KienTT
 * @modifer_date: Sep 15, 2021
 */
public class CheckRankExist implements ConstraintValidator<IRankCheckExist, String> {

	@Autowired
	private IRankRepository rankRepository;

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if (rankRepository == null) {
			return true;
		}
		return rankRepository.findByRankName(value) == null;
	}

}
