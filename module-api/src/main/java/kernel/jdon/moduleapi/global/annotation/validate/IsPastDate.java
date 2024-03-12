package kernel.jdon.moduleapi.global.annotation.validate;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import kernel.jdon.moduleapi.global.validate.PastDateValidator;

@Documented
@Constraint(validatedBy = PastDateValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface IsPastDate {
    String message() default "올바르지 않은 날짜입니다.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
