/**
 * 
 * @author PTrXuan
 * Created on Sep 25, 2021
 */
package com.vti.LoggingAOP;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Represents a NoLogging
 *
 *
 * @author PTrXuan Created on Sep 25, 2021
 */
@Documented
@Target({ ElementType.TYPE, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface NoLogging {

	boolean duration() default false;
}
