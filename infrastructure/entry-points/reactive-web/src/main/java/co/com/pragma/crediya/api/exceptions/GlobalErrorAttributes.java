package co.com.pragma.crediya.api.exceptions;

import co.com.pragma.crediya.model.user.exception.DocumentIdAlreadyExistsException;
import co.com.pragma.crediya.model.user.exception.EmailAlreadyExistsException;
import co.com.pragma.crediya.model.user.exception.RoleNotExistsException;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.reactive.error.DefaultErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;

import java.util.LinkedHashMap;
import java.util.Map;

@Component
public class GlobalErrorAttributes extends DefaultErrorAttributes {

    @Override
    public Map<String, Object> getErrorAttributes(ServerRequest request, ErrorAttributeOptions options) {

        Throwable ex = getError(request);

        int status = (ex instanceof EmailAlreadyExistsException
                || ex instanceof DocumentIdAlreadyExistsException || ex instanceof RoleNotExistsException)
                ? HttpStatus.CONFLICT.value() : HttpStatus.BAD_REQUEST.value();

        Map<String, Object> map = new LinkedHashMap<>();
        map.put("error", HttpStatus.valueOf(status).getReasonPhrase());
        map.put("message", ex.getMessage());
        map.put("path", request.path());
        map.put("status", status);
        map.put("timestamp", java.time.Instant.now().toString());

        return map;
    }
}
