package co.com.pragma.crediya.api.helper;


import co.com.pragma.crediya.model.user.exception.DocumentIdAlreadyExistsException;
import co.com.pragma.crediya.model.user.exception.EmailAlreadyExistsException;
import co.com.pragma.crediya.model.user.exception.InvalidBaseSalaryRangeException;
import co.com.pragma.crediya.model.user.exception.InvalidUserException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class ExceptionHelper {

    public HttpStatus resolveStatus(Throwable ex) {
        if (ex instanceof org.springframework.web.server.ResponseStatusException rse) {
            return HttpStatus.valueOf(rse.getStatusCode().value());
        }
        if (ex instanceof EmailAlreadyExistsException || ex instanceof DocumentIdAlreadyExistsException) {
            return HttpStatus.CONFLICT;
        }
        if (ex instanceof InvalidBaseSalaryRangeException) {
            return HttpStatus.UNPROCESSABLE_ENTITY;
        }

        if (ex instanceof InvalidUserException) {
            return HttpStatus.BAD_REQUEST;
        }

        return HttpStatus.INTERNAL_SERVER_ERROR;
    }
}
