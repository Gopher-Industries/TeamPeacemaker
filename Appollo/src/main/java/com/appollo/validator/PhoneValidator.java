package com.appollo.validator;


import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PhoneValidator implements ConstraintValidator<Phone, String> {

	@Override
	public void initialize(Phone paramA) {
	}

	@Override
	public boolean isValid(String phoneNo, ConstraintValidatorContext ctx) {
		if(phoneNo == null){
			return false;
		}
		//validate phone numbers of format "1234567890"
        if (phoneNo.matches("\\d{10}")) return true;
        //validating phone number with -, . or spaces
        else if(phoneNo.matches("\\d{4}[-\\.\\s]\\d{3}[-\\.\\s]\\d{3}")) return true;
        //validating phone number with extension length from 2 to 5
        else if(phoneNo.matches("^(\\+\\d{1,3}( )?)?((\\(\\d{3}\\))|\\d{3})[- .]?\\d{3}[- .]?\\d{3}$")) return true;
        //validating phone number where area code is in braces ()
        else if(phoneNo.matches("\\(\\d{4}\\)-\\d{3}-\\d{3}")) return true;
        //return false if nothing matches the input
        else return false;
	}

}