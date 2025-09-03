package co.com.pragma.crediya.r2dbc;

import co.com.pragma.crediya.model.user.User;
import co.com.pragma.crediya.model.user.exception.UserNotExistsException;
import co.com.pragma.crediya.model.user.gateways.UserRepository;
import co.com.pragma.crediya.r2dbc.entity.UserEntity;
import co.com.pragma.crediya.r2dbc.helper.ReactiveAdapterOperations;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.reactive.TransactionalOperator;
import reactor.core.publisher.Mono;

import java.util.UUID;

import static co.com.pragma.crediya.r2dbc.exception.UserDBException.valideDBException;

@Repository
public class UserReactiveRepositoryAdapter extends ReactiveAdapterOperations<
        User,
        UserEntity,
        UUID,
        UserReactiveRepository
        > implements UserRepository {
    public UserReactiveRepositoryAdapter(UserReactiveRepository repository,
                                         ObjectMapper mapper,
                                         TransactionalOperator transactionalOperator) {

        super(repository, mapper, d -> mapper.map(d, User.class));

        this.transactionalOperator = transactionalOperator;
    }

    private final TransactionalOperator transactionalOperator;

    @Override
    public Mono<User> saveUser(User user) {

        Mono<User> flow =
                super.save(user)
                        .onErrorMap(DataIntegrityViolationException.class,
                                ex -> valideDBException(ex, user));

        return transactionalOperator.transactional(flow);
    }

    @Override
    public Mono<User> findUserByDocumentId(String documentId){
        return repository.findByDocumentId(documentId)
                .map(this::toEntity)
                .onErrorMap(RuntimeException.class,
                        ex -> new UserNotExistsException(documentId));
    }

}
