package kernel.jdon.moduleapi.global.validate;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import kernel.jdon.moduleapi.global.annotation.validate.isPastDate;

public class PastDateValidator implements ConstraintValidator<isPastDate, String> {

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		LocalDate date = null;

		try {
			date = LocalDate.from(LocalDate.parse(value, DateTimeFormatter.ofPattern("yyyy-MM-dd")));
		} catch (Exception e) {
			return false;
		}

		if (date.isAfter(LocalDate.now())) {
			return false;
		}

		return true;
	}
}
