package pl.goral.psychotherapistofficerest.utils;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = NotTooYoungValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface NotTooYoung {
    String message() default "Patient must be at least 10 years old";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}