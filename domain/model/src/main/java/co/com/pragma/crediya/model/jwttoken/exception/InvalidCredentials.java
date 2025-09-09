package co.com.pragma.crediya.model.jwttoken.exception;

public class InvalidCredentials extends RuntimeException {

    public InvalidCredentials() {
        super("Credenciales Invalidas");
    }
}
