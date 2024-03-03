package kernel.jdon.moduleapi.global.validate;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import kernel.jdon.moduleapi.global.annotation.validate.IsValidNickname;

public class NicknameValidator implements ConstraintValidator<IsValidNickname, String> {

	@Override
	public boolean isValid(String nickname, ConstraintValidatorContext constraintValidatorContext) {
		return InvalidNickname.isInvalid(nickname);
	}
}
