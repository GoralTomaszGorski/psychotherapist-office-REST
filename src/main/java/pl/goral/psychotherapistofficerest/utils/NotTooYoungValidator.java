package pl.goral.psychotherapistofficerest.utils;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;

public class NotTooYoungValidator implements ConstraintValidator<NotTooYoung, Integer> {
    @Override
    public boolean isValid(Integer yearOfBirth, ConstraintValidatorContext context) {
        if (yearOfBirth == null) return false;
        int currentYear = LocalDate.now().getYear();
        return (currentYear - yearOfBirth) >= 5;
    }
}