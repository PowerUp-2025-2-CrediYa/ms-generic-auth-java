package co.com.pragma.crediya.r2dbc;

import co.com.pragma.crediya.model.user.User;
import co.com.pragma.crediya.model.user.exception.DocumentIdAlreadyExistsException;
import co.com.pragma.crediya.model.user.exception.EmailAlreadyExistsException;
import co.com.pragma.crediya.r2dbc.entity.UserEntity;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.transaction.reactive.TransactionalOperator;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UserReactiveRepositoryAdapterTest {

    UserReactiveRepository repository;
    TransactionalOperator tx;
    ObjectMapper mapper = mock(ObjectMapper.class);
    UserReactiveRepositoryAdapter adapter;

    /*@BeforeEach
    void setUp() {
        repository = mock(UserReactiveRepository.class);
        tx = mock(TransactionalOperator.class);

        when(tx.transactional(any(Mono.class)))
                .thenAnswer(inv -> inv.getArgument(0));

        mapper = new ObjectMapper() {
            @Override

            public <T> T map(Object source, Class<T> target) {
                if (source == null) return null;
                if (source instanceof User u && target.equals(UserEntity.class)) {
                    UserEntity e = new UserEntity();
                    e.setId(u.getId());
                    e.setEmail(u.getEmail());
                    e.setDocumentId(u.getDocumentId());
                    e.setFirstName(u.getFirstName());
                    return (T) e;
                }
                if (source instanceof UserEntity e && target.equals(User.class)) {
                    User u = new User();
                    u.setId(e.getId());
                    u.setEmail(e.getEmail());
                    u.setDocumentId(e.getDocumentId());
                    u.setFirstName(e.getFirstName());
                    return (T) u;
                }
                throw new IllegalArgumentException("Mapping no soportado: " + source.getClass() + " -> " + target);
            }

            @Override
            public <T> T mapBuilder(Object src, Class<T> target) {
                return null;
            }
        };

        adapter = new UserReactiveRepositoryAdapter(repository, mapper, tx);
    }*/

    @Test
    void saveUserAndReturnDomain() {
        User user = new User();
        user.setId(UUID.randomUUID());
        user.setEmail("ok@dom.com");
        user.setDocumentId("DOC1");
        user.setFirstName("Carlos");

        UserEntity savedEntity = new UserEntity();
        savedEntity.setId(user.getId());
        savedEntity.setEmail(user.getEmail());
        savedEntity.setDocumentId(user.getDocumentId());
        savedEntity.setFirstName(user.getFirstName());

        when(repository.save(any(UserEntity.class))).thenReturn(Mono.just(savedEntity));

        StepVerifier.create(adapter.saveUser(user))
                .assertNext(u -> {
                    assertThat(u.getId()).isEqualTo(user.getId());
                    assertThat(u.getEmail()).isEqualTo("ok@dom.com");
                    assertThat(u.getDocumentId()).isEqualTo("DOC1");
                })
                .verifyComplete();

        ArgumentCaptor<UserEntity> cap = ArgumentCaptor.forClass(UserEntity.class);
        verify(repository).save(cap.capture());
        assertThat(cap.getValue().getEmail()).isEqualTo("ok@dom.com");

        verify(tx).transactional(any(Mono.class));
    }

    @Test
    void saveUserIfEmailIsUniqueOrThrowEmailAlreadyExists() {
        User user = new User();
        user.setEmail("dup@dom.com");
        user.setDocumentId("DOC2");

        DataIntegrityViolationException dive = new DataIntegrityViolationException(
                "duplicate key value violates unique constraint \"usuarios_email_key\""
        );
        when(repository.save(any(UserEntity.class))).thenReturn(Mono.error(dive));

        StepVerifier.create(adapter.saveUser(user))
                .expectError(EmailAlreadyExistsException.class)
                .verify();

        verify(tx).transactional(any(Mono.class));
    }

    @Test
    void saveUserIfDocumentIsUniqueOrThrowDocumentAlreadyExists() {
        User user = new User();
        user.setEmail("ok@dom.com");
        user.setDocumentId("DOC-DUP");

        DataIntegrityViolationException dive = new DataIntegrityViolationException(
                "duplicate key value violates unique constraint \"usuarios_documento_identidad_key\""
        );
        when(repository.save(any(UserEntity.class))).thenReturn(Mono.error(dive));

        StepVerifier.create(adapter.saveUser(user))
                .expectError(DocumentIdAlreadyExistsException.class)
                .verify();

        verify(tx).transactional(any(Mono.class));
    }

    @Test
    void saveUser_rethrowsConstraintViolationException() {
        User user = new User();
        user.setEmail("x@y.com");
        user.setDocumentId("D1");

        DataIntegrityViolationException dive = new DataIntegrityViolationException(
                "duplicate key value violates unique constraint \"uk_unknown\""
        );
        when(repository.save(any(UserEntity.class))).thenReturn(Mono.error(dive));

        StepVerifier.create(adapter.saveUser(user))
                .expectError(DataIntegrityViolationException.class)
                .verify();
    }
}
