package com.vega.springit.domain.validator;

import com.vega.springit.domain.AppUser;
import org.apache.catalina.User;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordsMatchValidator implements ConstraintValidator<PasswordsMatch, AppUser> {
    @Override
    public void initialize(PasswordsMatch constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(AppUser user, ConstraintValidatorContext context) {
        return user.getPassword().equals(user.getConfirmPassword());
    }
}
