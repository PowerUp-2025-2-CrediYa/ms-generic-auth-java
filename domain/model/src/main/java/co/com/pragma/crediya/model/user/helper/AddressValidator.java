package co.com.pragma.crediya.model.user.helper;

import co.com.pragma.crediya.model.user.exception.InvalidAddressException;

import java.util.regex.Pattern;

public final class AddressValidator {

    private static final Pattern ADDRESS_PATTERN = Pattern.compile(
            "^[A-Za-zÁÉÍÓÚÜÑáéíóúüñ0-9#\\-.,ºª/\\s]{1,255}$"
    );

    private AddressValidator() {}

    public static void validate(String address) {
        if (address == null || address.trim().isEmpty()) {
            throw new InvalidAddressException("Dirección requerida");
        }

        if (!ADDRESS_PATTERN.matcher(address.trim()).matches()) {
            throw new InvalidAddressException("Dirección con formato inválido");
        }
    }
}

