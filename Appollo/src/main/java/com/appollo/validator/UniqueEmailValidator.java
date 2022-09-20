package com.appollo.validator;


import org.springframework.beans.factory.annotation.Autowired;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import com.appollo.actions.UserActions;

public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {

	@Autowired
	UserActions actions;

	@Override
	public void initialize(UniqueEmail email) {
	}
	
	@Override
	public boolean isValid(String email, ConstraintValidatorContext context) {
		return email != null && !actions.isEmailAlreadyInUse(email);
	}
	
}