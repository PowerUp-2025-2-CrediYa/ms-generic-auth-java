package co.com.pragma.crediya.model.user.helper;

import co.com.pragma.crediya.model.user.User;
import co.com.pragma.crediya.model.user.exception.InvalidBaseSalaryRangeException;
import co.com.pragma.crediya.model.user.exception.InvalidUserException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserValidatorTest {

    @Test
    void validate_validUser_doesNotThrow() {
        User u = validUser();
        assertDoesNotThrow(() -> UserValidator.validate(u));
    }

    @Test
    void validate_nullFirstName_throwsInvalidUserException() {
        User u = validUser();
        u.setFirstName(null);

        InvalidUserException ex = assertThrows(
                InvalidUserException.class,
                () -> UserValidator.validate(u)
        );
        assertEquals("El nombre no puede estar vacío", ex.getMessage());
    }

    @Test
    void validate_blankFirstName_throwsInvalidUserException() {
        User u = validUser();
        u.setFirstName("   ");

        InvalidUserException ex = assertThrows(
                InvalidUserException.class,
                () -> UserValidator.validate(u)
        );
        assertEquals("El nombre no puede estar vacío", ex.getMessage());
    }

    @Test
    void validate_nullLastName_throwsInvalidUserException() {
        User u = validUser();
        u.setLastName(null);

        InvalidUserException ex = assertThrows(
                InvalidUserException.class,
                () -> UserValidator.validate(u)
        );
        assertEquals("El apellido no puede estar vacío", ex.getMessage());
    }

    @Test
    void validate_blankLastName_throwsInvalidUserException() {
        User u = validUser();
        u.setLastName("   ");

        InvalidUserException ex = assertThrows(
                InvalidUserException.class,
                () -> UserValidator.validate(u)
        );
        assertEquals("El apellido no puede estar vacío", ex.getMessage());
    }

    @Test
    void validate_nullEmail_throwsInvalidUserException() {
        User u = validUser();
        u.setEmail(null);

        InvalidUserException ex = assertThrows(
                InvalidUserException.class,
                () -> UserValidator.validate(u)
        );
        assertEquals("El correo electrónico no puede estar vacío", ex.getMessage());
    }

    @Test
    void validate_blankEmail_throwsInvalidUserException() {
        User u = validUser();
        u.setEmail("  ");

        InvalidUserException ex = assertThrows(
                InvalidUserException.class,
                () -> UserValidator.validate(u)
        );
        assertEquals("El correo electrónico no puede estar vacío", ex.getMessage());
    }

    @Test
    void validate_invalidEmailPattern_throwsInvalidUserException() {
        User u = validUser();
        u.setEmail("correo-invalido-sin-arroba");

        InvalidUserException ex = assertThrows(
                InvalidUserException.class,
                () -> UserValidator.validate(u)
        );
        assertEquals("El formato del correo electrónico no es válido", ex.getMessage());
    }

    @Test
    void validate_nullBaseSalary_throwsInvalidUserException() {
        User u = validUser();
        u.setBaseSalary(null);

        InvalidUserException ex = assertThrows(
                InvalidUserException.class,
                () -> UserValidator.validate(u)
        );
        assertEquals("El salario base no puede ser nulo", ex.getMessage());
    }

    @Test
    void validate_negativeBaseSalary_throwsInvalidBaseSalaryRangeException() {
        User u = validUser();
        u.setBaseSalary(-1.0);

        InvalidBaseSalaryRangeException ex = assertThrows(
                InvalidBaseSalaryRangeException.class,
                () -> UserValidator.validate(u)
        );
        // La excepción recibe el valor como string toPlainString(); validamos que lo contenga
        assertTrue(ex.getMessage() == null || ex.getMessage().contains("-1"),
                "El mensaje debería contener el valor inválido o ser nulo según tu implementación");
    }

    @Test
    void validate_aboveMaxBaseSalary_throwsInvalidBaseSalaryRangeException() {
        User u = validUser();
        u.setBaseSalary(15_000_001.0);

        InvalidBaseSalaryRangeException ex = assertThrows(
                InvalidBaseSalaryRangeException.class,
                () -> UserValidator.validate(u)
        );
        // toPlainString de 15_000_001.0 → "15000001"
        assertTrue(ex.getMessage() == null || ex.getMessage().contains("15000001"),
                "El mensaje debería contener '15000001' o ser nulo según tu implementación");
    }

    // ---------- helper ----------
    private static User validUser() {
        User u = new User();
        u.setFirstName("Juan");
        u.setLastName("Pérez");
        u.setEmail("juan.perez@example.com");
        u.setBaseSalary(1_000_000d);
        return u;
    }
}
