package co.com.pragma.crediya.r2dbc;

import co.com.pragma.crediya.model.user.User;
import co.com.pragma.crediya.model.user.gateways.UserRepository;
import co.com.pragma.crediya.r2dbc.entity.UserEntity;
import co.com.pragma.crediya.r2dbc.helper.ReactiveAdapterOperations;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Repository
public class UserReactiveRepositoryAdapter extends ReactiveAdapterOperations<
        User,
        UserEntity,
        UUID,
        UserReactiveRepository
        > implements UserRepository {
    public UserReactiveRepositoryAdapter(UserReactiveRepository repository, ObjectMapper mapper) {
        super(repository, mapper, d -> mapper.map(d, User.class));
    }

    @Override
    public Mono<User> saveUser(User user) {

        return super.save(user);
    }

    @Override
    public Mono<Boolean> existsByEmail(String email) {

        return repository.existsByEmail(email);

    }

    @Override
    public Mono<Boolean> existsByDocumentId(String documentId) {

        return repository.existsByDocumentId(documentId);
    }

}
