/**
 * 
 */
package com.jspmyadmin.framework.web.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *
 * _at 2016/01/29
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(value = ElementType.METHOD)
public @interface HandleGet {

}