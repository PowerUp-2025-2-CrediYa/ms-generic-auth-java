package co.com.pragma.crediya.model.user.helper;

import co.com.pragma.crediya.model.user.User;
import org.junit.jupiter.params.provider.Arguments;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;
import java.util.stream.Stream;

public final class UserValidatorUtils {

    static User validUser() {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate fechaNacimiento = LocalDate.parse("01/01/1990", formatter);

        User user = new User();
        user.setId(UUID.randomUUID());
        user.setFirstName("Jon");
        user.setLastName("Doe");
        user.setEmail("ok@dom.com");
        user.setDocumentId("123456789");
        user.setBirthDate(fechaNacimiento);
        user.setAddress("Calle 44 # 11-33");
        user.setPhoneNumber("1234568910");
        user.setRoleId(1);
        user.setBaseSalary(Double.valueOf("1000000"));
        return user;
    }


    public static Stream<Arguments> invalidEmails() {
        return Stream.of(
                Arguments.of(null, "El correo electrónico no puede estar vacío"),
                Arguments.of("  ", "El correo electrónico no puede estar vacío"),
                Arguments.of("correo-invalido-sin-arroba", "El formato del correo electrónico no es válido")
        );
    }

}
