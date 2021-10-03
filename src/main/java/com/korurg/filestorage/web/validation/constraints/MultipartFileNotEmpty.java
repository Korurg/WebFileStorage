package com.korurg.filestorage.web.validation.constraints;

import com.korurg.filestorage.web.validation.implementation.MultipartFileNotEmptyValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Constraint(validatedBy = {MultipartFileNotEmptyValidator.class})
@Target({METHOD, FIELD, PARAMETER})
@Retention(RUNTIME)
public @interface MultipartFileNotEmpty {

    String message() default "{File must be not empty}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
