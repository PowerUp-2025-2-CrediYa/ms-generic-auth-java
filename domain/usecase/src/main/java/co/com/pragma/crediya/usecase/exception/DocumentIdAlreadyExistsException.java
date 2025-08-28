package co.com.pragma.crediya.usecase.exception;

public class DocumentIdAlreadyExistsException extends RuntimeException{
    public DocumentIdAlreadyExistsException(String documentId) {
        super("Ya existe un usuario con el documento: " + documentId);
    }

}
