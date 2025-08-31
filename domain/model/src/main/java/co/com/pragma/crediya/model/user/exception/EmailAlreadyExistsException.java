package co.com.pragma.crediya.model.user.exception;

public class EmailAlreadyExistsException extends RuntimeException{

    public EmailAlreadyExistsException(String email) {
        super("Ya existe un usuario con el email: ".concat(email));
    }

}
