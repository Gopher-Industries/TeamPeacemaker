package com.appollo.validator;

import java.lang.annotation.*;
import javax.validation.*;

@Constraint(validatedBy = UniqueEmailValidator.class)
@Retention(RetentionPolicy.RUNTIME)
@Target( { ElementType.METHOD, ElementType.FIELD })
public @interface UniqueEmail {

	public String message() default "There is already user with this email!";
	
	public Class<?>[] groups() default {};
	
	public Class<? extends Payload>[] payload() default{};

}