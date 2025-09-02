package co.com.pragma.crediya.model.user.helper;

import co.com.pragma.crediya.model.user.User;
import co.com.pragma.crediya.model.user.exception.InvalidUserException;

public final class UserValidator {

    private UserValidator() {}

    public static void validate(User user) {
        if (isNullOrEmpty(user.getFirstName())) {
            throw new InvalidUserException("El nombre no puede estar vacío");
        }

        if (isNullOrEmpty(user.getLastName())) {
            throw new InvalidUserException("El apellido no puede estar vacío");
        }

        EmailValidator.validate(user.getEmail());
        SalaryValidator.validate(user.getBaseSalary());
        AddressValidator.validate(user.getAddress());
        BirthDateValidator.validate(String.valueOf(user.getBirthDate()));
    }

    private static boolean isNullOrEmpty(String value) {
        return value == null || value.trim().isEmpty();
    }
}

