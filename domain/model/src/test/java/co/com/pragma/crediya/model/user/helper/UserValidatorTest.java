package co.com.pragma.crediya.model.user.helper;

import co.com.pragma.crediya.model.user.User;
import co.com.pragma.crediya.model.user.exception.InvalidBaseSalaryRangeException;
import co.com.pragma.crediya.model.user.exception.InvalidUserException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static org.junit.jupiter.api.Assertions.*;

class UserValidatorTest {

    @Test
    void validate_validUser_doesNotThrow() {
        User u = UserValidatorUtils.validUser();
        assertDoesNotThrow(() -> UserValidator.validate(u));
    }

    @Test
    void validate_nullFirstName_throwsInvalidUserException() {
        User u = UserValidatorUtils.validUser();
        u.setFirstName(null);

        InvalidUserException ex = assertThrows(
                InvalidUserException.class,
                () -> UserValidator.validate(u)
        );
        assertEquals("El nombre no puede estar vacío", ex.getMessage());
    }

    @Test
    void validate_blankFirstName_throwsInvalidUserException() {
        User u = UserValidatorUtils.validUser();
        u.setFirstName("   ");

        InvalidUserException ex = assertThrows(
                InvalidUserException.class,
                () -> UserValidator.validate(u)
        );
        assertEquals("El nombre no puede estar vacío", ex.getMessage());
    }

    @Test
    void validate_nullLastName_throwsInvalidUserException() {
        User u = UserValidatorUtils.validUser();
        u.setLastName(null);

        InvalidUserException ex = assertThrows(
                InvalidUserException.class,
                () -> UserValidator.validate(u)
        );
        assertEquals("El apellido no puede estar vacío", ex.getMessage());
    }

    @Test
    void validate_blankLastName_throwsInvalidUserException() {
        User u = UserValidatorUtils.validUser();
        u.setLastName("   ");

        InvalidUserException ex = assertThrows(
                InvalidUserException.class,
                () -> UserValidator.validate(u)
        );
        assertEquals("El apellido no puede estar vacío", ex.getMessage());
    }

    @ParameterizedTest
    @MethodSource("co.com.pragma.crediya.model.user.helper.UserValidatorUtils#invalidEmails")
    void validate_invalidEmail_throwsInvalidUserException(String email, String expectedMessage) {
        User u = UserValidatorUtils.validUser();
        u.setEmail(email);

        InvalidUserException ex = assertThrows(
                InvalidUserException.class,
                () -> UserValidator.validate(u)
        );

        assertEquals(expectedMessage, ex.getMessage());
    }

    @Test
    void validate_nullBaseSalary_throwsInvalidUserException() {
        User u = UserValidatorUtils.validUser();
        u.setBaseSalary(null);

        InvalidUserException ex = assertThrows(
                InvalidUserException.class,
                () -> UserValidator.validate(u)
        );
        assertEquals("El salario base no puede ser nulo", ex.getMessage());
    }

    @Test
    void validate_negativeBaseSalary_throwsInvalidBaseSalaryRangeException() {
        User u = UserValidatorUtils.validUser();
        u.setBaseSalary(-1.0);

        InvalidBaseSalaryRangeException ex = assertThrows(
                InvalidBaseSalaryRangeException.class,
                () -> UserValidator.validate(u)
        );

        assertTrue(ex.getMessage() == null || ex.getMessage().contains("-1"),
                "El mensaje debería contener el valor inválido o ser nulo según tu implementación");
    }

    @Test
    void validate_aboveMaxBaseSalary_throwsInvalidBaseSalaryRangeException() {
        User u = UserValidatorUtils.validUser();
        u.setBaseSalary(15_000_001.0);

        InvalidBaseSalaryRangeException ex = assertThrows(
                InvalidBaseSalaryRangeException.class,
                () -> UserValidator.validate(u)
        );

        assertTrue(ex.getMessage() == null || ex.getMessage().contains("15000001"),
                "El mensaje debería contener '15000001' o ser nulo según tu implementación");
    }

}
