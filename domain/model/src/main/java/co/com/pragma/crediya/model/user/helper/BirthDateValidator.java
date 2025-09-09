package co.com.pragma.crediya.model.user.helper;

import co.com.pragma.crediya.model.user.exception.InvalidBirthDateException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Locale;

public final class BirthDateValidator {

    private static final List<DateTimeFormatter> FORMATTERS = List.of(
            DateTimeFormatter.ofPattern("dd/MM/yyyy"),
            DateTimeFormatter.ofPattern("yyyy-MM-dd"),
            DateTimeFormatter.ofPattern("d-M-yyyy")
    );

    private BirthDateValidator() {
    }

    public static void validate(String birthDate) {
        if (birthDate == null || birthDate.trim().isEmpty()) {
            throw new InvalidBirthDateException("Fecha de nacimiento requerida");
        }

        String normalized = birthDate.trim().toLowerCase(Locale.of("es", "CO"));

        boolean isValid = FORMATTERS.stream()
                .anyMatch(formatter -> isParsable(normalized, formatter));

        if (!isValid) {
            throw new InvalidBirthDateException("Formato de fecha inválido");
        }
    }

    private static boolean isParsable(String date, DateTimeFormatter formatter) {
        try {
            LocalDate.parse(date, formatter);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }
}

