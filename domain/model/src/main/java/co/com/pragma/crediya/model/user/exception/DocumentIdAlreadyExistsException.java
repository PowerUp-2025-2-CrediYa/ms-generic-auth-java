package co.com.pragma.crediya.model.user.exception;

public class DocumentIdAlreadyExistsException extends RuntimeException{

    public DocumentIdAlreadyExistsException(String documentId) {
        super("Ya existe un usuario con el documento: ".concat(documentId));
    }

}
