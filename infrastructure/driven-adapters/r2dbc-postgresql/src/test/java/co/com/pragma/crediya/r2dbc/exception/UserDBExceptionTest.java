package co.com.pragma.crediya.r2dbc.exception;

import co.com.pragma.crediya.model.user.User;
import co.com.pragma.crediya.model.user.exception.DocumentIdAlreadyExistsException;
import co.com.pragma.crediya.model.user.exception.EmailAlreadyExistsException;
import co.com.pragma.crediya.model.user.exception.RoleNotExistsException;
import io.r2dbc.spi.R2dbcException;
import org.junit.jupiter.api.Test;
import org.springframework.dao.DataIntegrityViolationException;

import static co.com.pragma.crediya.r2dbc.exception.UserDBException.valideDBException;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class UserDBExceptionTest {

    @Test
    void uniqueViolation_bySqlState_emailConstraint_mapsToEmailAlreadyExists() {
        String msg = "duplicate key value violates unique constraint \"usuarios_email_key\"";
        R2dbcException r2 = new R2dbcException(msg, "23505", 0) {
        };
        DataIntegrityViolationException dive = new DataIntegrityViolationException(msg, r2);

        User u = newUser("dup@dom.com", "DOC1", 1);

        RuntimeException mapped = valideDBException(dive, u);

        assertThat(mapped).isInstanceOf(EmailAlreadyExistsException.class);
    }

    @Test
    void uniqueViolation_bySqlState_documentConstraint_mapsToDocumentIdAlreadyExists() {
        String msg = "duplicate key value violates unique constraint \"usuarios_documento_identidad_key\"";
        R2dbcException r2 = new R2dbcException(msg, "23505", 0) {
        };
        DataIntegrityViolationException dive = new DataIntegrityViolationException(msg, r2);

        User u = newUser("ok@dom.com", "DOC-DUP", 1);

        RuntimeException mapped = valideDBException(dive, u);

        assertThat(mapped).isInstanceOf(DocumentIdAlreadyExistsException.class);
    }

    @Test
    void uniqueViolation_byMessageOnly_emailConstraint_mapsToEmailAlreadyExists() {
        String msg = "duplicate key value violates unique constraint \"usuarios_email_key\"";
        DataIntegrityViolationException dive = new DataIntegrityViolationException(msg);

        User u = newUser("dup@dom.com", "DOC1", 1);

        RuntimeException mapped = valideDBException(dive, u);

        assertThat(mapped).isInstanceOf(EmailAlreadyExistsException.class);
    }

    @Test
    void uniqueViolation_byMessageOnly_documentConstraint_mapsToDocumentIdAlreadyExists() {
        String msg = "duplicate key value violates unique constraint \"usuarios_document_id_key\"";
        DataIntegrityViolationException dive = new DataIntegrityViolationException(msg);

        User u = newUser("ok@dom.com", "DOC-DUP", 1);

        RuntimeException mapped = valideDBException(dive, u);

        assertThat(mapped).isInstanceOf(DocumentIdAlreadyExistsException.class);
    }

    @Test
    void foreignKeyViolation_bySqlState_roleConstraint_mapsToRoleNotExists() {
        String msg = "insert or update on table \"usuarios\" violates foreign key constraint \"usuarios_id_rol_fkey\"";
        R2dbcException r2 = new R2dbcException(msg, "23503", 0) {
        };
        DataIntegrityViolationException dive = new DataIntegrityViolationException(msg, r2);

        User u = newUser("ok@dom.com", "DOC1", 2);

        RuntimeException mapped = valideDBException(dive, u);

        assertThat(mapped).isInstanceOf(RoleNotExistsException.class);
    }

    @Test
    void unknownConstraint_returnsOriginalDataIntegrityViolationException() {
        String msg = "duplicate key value violates unique constraint \"uk_desconocida\"";
        R2dbcException r2 = new R2dbcException(msg, "23505", 0) {
        };
        DataIntegrityViolationException dive = new DataIntegrityViolationException(msg, r2);

        User u = newUser("x@y.com", "DOCX", 1);

        RuntimeException mapped = valideDBException(dive, u);

        assertThat(mapped).isSameAs(dive);
    }

    @Test
    void prefersR2dbcMessageOverRootMessage() {
        String rootMsg = "duplicate key value violates unique constraint \"usuarios_documento_identidad_key\"";
        String r2Msg = "duplicate key value violates unique constraint \"usuarios_email_key\"";

        Throwable root = new RuntimeException(rootMsg);
        R2dbcException r2 = new R2dbcException(r2Msg, "23505", 0, root) {
        };
        DataIntegrityViolationException dive = new DataIntegrityViolationException("wrapper", r2);

        User u = newUser("dup@dom.com", "DOC1", 1);

        RuntimeException mapped = valideDBException(dive, u);

        assertThat(mapped).isInstanceOf(EmailAlreadyExistsException.class);
    }

    @Test
    void nullMessages_fallbackToOriginal() {
        R2dbcException r2 = new R2dbcException(null, "23505", 0) {
        };
        DataIntegrityViolationException dive = new DataIntegrityViolationException(null, r2);

        User u = newUser("x@y.com", "DOCX", 1);

        RuntimeException mapped = valideDBException(dive, u);

        assertThat(mapped).isSameAs(dive);
    }

    private static User newUser(String email, String documentId, Integer roleId) {
        User u = new User();
        u.setEmail(email);
        u.setDocumentId(documentId);
        u.setRoleId(roleId);
        return u;
    }

}
