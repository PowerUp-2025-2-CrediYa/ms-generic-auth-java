package co.com.pragma.crediya.api.helper;


import co.com.pragma.crediya.model.user.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class ExceptionHelper {

    public HttpStatus resolveStatus(Throwable ex) {

        if (ex instanceof org.springframework.web.server.ResponseStatusException rse) {
            return HttpStatus.valueOf(rse.getStatusCode().value());
        }
        if (ex instanceof EmailAlreadyExistsException
                || ex instanceof DocumentIdAlreadyExistsException
                || ex instanceof RoleNotExistsException
        ) {
            return HttpStatus.CONFLICT;
        }
        if (ex instanceof InvalidBaseSalaryRangeException) {
            return HttpStatus.UNPROCESSABLE_ENTITY;
        }

        if (ex instanceof InvalidUserException) {
            return HttpStatus.BAD_REQUEST;
        }

        if (ex instanceof UserNotExistsException){
            return HttpStatus.NOT_FOUND;
        }

        return HttpStatus.INTERNAL_SERVER_ERROR;
    }
}
