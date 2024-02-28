package kernel.jdon.moduleapi.global.validate;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import kernel.jdon.moduledomain.member.domain.Gender;

public class GenderValidator
	implements ConstraintValidator<kernel.jdon.moduleapi.global.annotation.validate.Gender, String> {

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if (null == Gender.ofType(value)) {
			return false;
		}
		return true;
	}
}
