package co.com.pragma.crediya.r2dbc.exception;

import co.com.pragma.crediya.model.user.User;
import co.com.pragma.crediya.model.user.exception.DocumentIdAlreadyExistsException;
import co.com.pragma.crediya.model.user.exception.EmailAlreadyExistsException;
import co.com.pragma.crediya.model.user.exception.RoleNotExistsException;
import org.springframework.dao.DataIntegrityViolationException;

public class UserDBException extends RuntimeException {
    UserDBException() {}

    public static RuntimeException valideDBException(DataIntegrityViolationException exception, User user) {

        Throwable cause = exception.getCause();
        if (cause instanceof io.r2dbc.spi.R2dbcException r2
                && ("23505".equals(r2.getSqlState()) || "23503".equals(r2.getSqlState()))) {

            String msg = r2.getMessage();
            if (msg != null) {
                if (msg.contains("usuarios_email_key"))
                    return new EmailAlreadyExistsException(user.getEmail());

                if (msg.contains("usuarios_documento_identidad_key"))
                    return new DocumentIdAlreadyExistsException(user.getDocumentId());

                if (msg.contains("usuarios_id_rol_fkey"))
                    return new RoleNotExistsException(String.valueOf(user.getRoleId()));

            }
        }

        return exception;
    }
}
