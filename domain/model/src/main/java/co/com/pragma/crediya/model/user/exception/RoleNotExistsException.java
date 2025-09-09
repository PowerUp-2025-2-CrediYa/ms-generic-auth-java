package co.com.pragma.crediya.model.user.exception;

public class RoleNotExistsException extends RuntimeException {

    public RoleNotExistsException(String role) {
        super("El role no existe: ".concat(role));
    }

}
