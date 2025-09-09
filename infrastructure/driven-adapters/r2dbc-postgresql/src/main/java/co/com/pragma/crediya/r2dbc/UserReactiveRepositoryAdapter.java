package co.com.pragma.crediya.r2dbc;

import co.com.pragma.crediya.model.user.User;
import co.com.pragma.crediya.model.user.exception.UserNotExistsException;
import co.com.pragma.crediya.model.user.gateways.UserRepository;
import co.com.pragma.crediya.r2dbc.entity.UserEntity;
import co.com.pragma.crediya.r2dbc.exception.UserDBException;
import co.com.pragma.crediya.r2dbc.helper.ReactiveAdapterOperations;
import co.com.pragma.crediya.r2dbc.mappers.UserMapper;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.reactive.TransactionalOperator;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static co.com.pragma.crediya.r2dbc.mappers.UserMapper.toDomain;

@Repository
public class UserReactiveRepositoryAdapter extends ReactiveAdapterOperations<
        User,
        UserEntity,
        UUID,
        UserReactiveRepository
        > implements UserRepository {
    public UserReactiveRepositoryAdapter(UserReactiveRepository repository,
                                         ObjectMapper mapper,
                                         TransactionalOperator transactionalOperator, RolesRepositoryAdapter userRoleQueries) {

        super(repository, mapper, d -> mapper.map(d, User.class));

        this.transactionalOperator = transactionalOperator;
        this.userRoleQueries = userRoleQueries;
    }

    private final TransactionalOperator transactionalOperator;
    private final RolesRepositoryAdapter userRoleQueries;

    @Override
    public Mono<User> saveUser(User user) {
        List<String> roles = (user.getRoles() == null)
                ? List.of()
                : user.getRoles().stream()
                .filter(Objects::nonNull)
                .map(String::trim)
                .filter(s -> !s.isBlank())
                .distinct()
                .toList();


        Mono<User> flow =
                super.save(user)
                        .flatMap(saved ->
                                userRoleQueries.replaceUserRoles(saved.getId(), roles)
                                        .then(Mono.just(saved))
                        )
                        .flatMap(saved ->
                                userRoleQueries.findRoleCodesByUserId(saved.getId())
                                        .collectList()
                                        .map(codes -> toDomain(UserMapper.toEntity((saved)), codes))
                        )
                        .onErrorMap(DataIntegrityViolationException.class, ex -> UserDBException.valideDBException(ex, user));

        return transactionalOperator.transactional(flow);
    }

    @Override
    public Mono<User> findUserByDocumentId(String documentId) {
        return repository.findByDocumentId(documentId)
                .map(this::toEntity)
                .onErrorMap(RuntimeException.class,
                        ex -> new UserNotExistsException(documentId));
    }

    @Override
    public Mono<User> findByEmail(String email) {
        return repository.findByEmail(email)
                .flatMap(data -> userRoleQueries.findRoleCodesByUserId(data.getId())
                        .collectList()
                        .map(codes -> toDomain(data, codes)));
    }

}
