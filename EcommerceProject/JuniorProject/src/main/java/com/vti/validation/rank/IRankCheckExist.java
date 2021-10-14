/**
 * 
 */
package com.vti.validation.rank;

import static java.lang.annotation.ElementType.FIELD;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

/**
 * This class is IRankCheckExist
 * 
 * @Description: ...
 * @author: KienTT
 * @create_date: Sep 15, 2021
 * @version: 1.0
 * @modifer: KienTT
 * @modifer_date: Sep 15, 2021
 */
@Target({ FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = { CheckRankExist.class })
public @interface IRankCheckExist {
	String message() default "Rank name realdy existed";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

}
