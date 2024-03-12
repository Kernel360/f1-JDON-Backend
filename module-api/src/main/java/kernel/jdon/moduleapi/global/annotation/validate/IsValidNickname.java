package kernel.jdon.moduleapi.global.annotation.validate;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import kernel.jdon.moduleapi.global.validate.NicknameValidator;

@Documented
@Constraint(validatedBy = NicknameValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface IsValidNickname {
    String message() default "사용할 수 없는 닉네임입니다.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
