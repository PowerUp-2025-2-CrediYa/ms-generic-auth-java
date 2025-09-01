package co.com.pragma.crediya.r2dbc.exception;

import co.com.pragma.crediya.model.user.User;
import co.com.pragma.crediya.model.user.exception.DocumentIdAlreadyExistsException;
import co.com.pragma.crediya.model.user.exception.EmailAlreadyExistsException;
import co.com.pragma.crediya.model.user.exception.RoleNotExistsException;
import io.r2dbc.spi.R2dbcException;
import org.springframework.dao.DataIntegrityViolationException;
import reactor.core.Exceptions;

public class UserDBException extends RuntimeException {
    UserDBException() {}

    // ---- Punto de entrada con baja complejidad ----
    public static RuntimeException valideDBException(DataIntegrityViolationException exception, User user) {
        DbError db = extractDbError(exception);

        if (isUniqueViolation(db)) {
            RuntimeException mapped = mapUniqueViolation(db.message(), user);
            if (mapped != null) return mapped;
        }

        if (isForeignKeyViolation(db)) {
            RuntimeException mapped = mapForeignKeyViolation(db.message(), user);
            if (mapped != null) return mapped;
        }

        return exception; // fallback
    }

    // ---- Helpers “pequeños” (no cuentan en la complejidad del método principal) ----

    private static RuntimeException mapUniqueViolation(String msg, User u) {
        if (contains(msg, "usuarios_email_key")) {
            return new EmailAlreadyExistsException(u.getEmail());
        }
        if (contains(msg, "usuarios_documento_identidad_key")
                || contains(msg, "usuarios_document_id_key")) {
            return new DocumentIdAlreadyExistsException(u.getDocumentId());
        }
        return null;
    }

    private static RuntimeException mapForeignKeyViolation(String msg, User u) {
        if (contains(msg, "usuarios_id_rol_fkey")) {
            return new RoleNotExistsException(String.valueOf(u.getRoleId()));
        }
        return null;
    }

    private static boolean isUniqueViolation(DbError db) {
        return "23505".equals(db.sqlState()) || containsUniqueKeyword(db.message());
    }

    private static boolean isForeignKeyViolation(DbError db) {
        return "23503".equals(db.sqlState()) || containsForeignKeyKeyword(db.message());
    }

    private static DbError extractDbError(DataIntegrityViolationException exception) {
        Throwable root = Exceptions.unwrap(exception);
        R2dbcException r2 = findCause(root, R2dbcException.class);

        String sqlState = (r2 != null) ? r2.getSqlState() : null;

        String msg;
        if (r2 != null && r2.getMessage() != null) {
            msg = r2.getMessage();
        } else if (root.getMessage() != null) {
            msg = root.getMessage();
        } else {
            msg = exception.getMessage();
        }

        return new DbError(sqlState, msg);
    }

    private static boolean containsUniqueKeyword(String msg) {
        if (msg == null) return false;
        String m = msg.toLowerCase();
        return m.contains("unique constraint")
                || m.contains("duplicate key")
                || m.contains("violates unique constraint")
                || (m.contains("duplicate") && m.contains("key"));
    }

    private static boolean containsForeignKeyKeyword(String msg) {
        if (msg == null) return false;
        String m = msg.toLowerCase();
        return m.contains("foreign key")
                || m.contains("violates foreign key constraint");
    }

    private static boolean contains(String msg, String needle) {
        return msg != null && msg.contains(needle);
    }

    @SuppressWarnings("unchecked")
    private static <T extends Throwable> T findCause(Throwable t, Class<T> type) {
        while (t != null && t.getCause() != t) {
            if (type.isInstance(t)) return (T) t;
            t = t.getCause();
        }
        return null;
    }

    // Java 16+ record para agrupar datos
    private record DbError(String sqlState, String message) {}
}
