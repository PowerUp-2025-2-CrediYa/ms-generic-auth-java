package co.com.pragma.crediya.model.user.validation;

import co.com.pragma.crediya.model.user.User;
import co.com.pragma.crediya.model.user.exception.InvalidBaseSalaryRangeException;
import co.com.pragma.crediya.model.user.exception.InvalidUserException;

import java.math.BigDecimal;
import java.util.regex.Pattern;

public class UserValidator {

    private UserValidator(){}

    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$"
    );

    public static void validate(User user) {
        if (isNullOrEmpty(user.getFirstName())) {
            throw new InvalidUserException("El nombre no puede estar vacío");
        }
        if (isNullOrEmpty(user.getLastName())) {
            throw new InvalidUserException("El apellido no puede estar vacío");
        }
        if (isNullOrEmpty(user.getEmail())) {
            throw new InvalidUserException("El correo electrónico no puede estar vacío");
        }
        if (!EMAIL_PATTERN.matcher(user.getEmail()).matches()) {
            throw new InvalidUserException("El formato del correo electrónico no es válido");
        }
        if (user.getBaseSalary() == null) {
            throw new InvalidUserException("El salario base no puede ser nulo");
        }
        if (user.getBaseSalary() < 0 || user.getBaseSalary() > 15_000_000) {
            throw new InvalidBaseSalaryRangeException(BigDecimal.valueOf(user.getBaseSalary())
                    .stripTrailingZeros()
                    .toPlainString());
        }
    }

    private static boolean isNullOrEmpty(String value) {
        return value == null || value.trim().isEmpty();
    }

}
