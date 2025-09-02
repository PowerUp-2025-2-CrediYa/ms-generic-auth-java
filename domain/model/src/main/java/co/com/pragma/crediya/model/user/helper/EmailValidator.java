package co.com.pragma.crediya.model.user.helper;

import co.com.pragma.crediya.model.user.exception.InvalidUserException;

import java.util.regex.Pattern;

public final class EmailValidator {

    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$"
    );

    private EmailValidator() {}

    public static void validate(String email) {
        if (email == null || email.trim().isEmpty()) {
            throw new InvalidUserException("El correo electrónico no puede estar vacío");
        }

        if (!EMAIL_PATTERN.matcher(email.trim()).matches()) {
            throw new InvalidUserException("El formato del correo electrónico no es válido");
        }
    }
}
