package com.vti.validation.passwordValid;
import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.TYPE_USE;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

/**
 * constraint passwd validate class
 * @author VuTV 
 * Created on 2021-09-29
 */
@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = { ComfirmPasswd.class })
public @interface ComfirmPasswdValid {

	String message() default "Passwords do not match";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

    String field();

    String fieldMatch();

	@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE })
	@Retention(RetentionPolicy.RUNTIME)
	@Documented
	@interface List {
		ComfirmPasswdValid[] value();
	}
}
