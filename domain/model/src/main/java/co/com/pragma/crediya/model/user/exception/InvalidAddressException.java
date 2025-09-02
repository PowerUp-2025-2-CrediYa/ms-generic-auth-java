package co.com.pragma.crediya.model.user.exception;

public class InvalidAddressException extends RuntimeException{

    public InvalidAddressException(String msg){
        super(msg);
    }
}
