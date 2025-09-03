package co.com.pragma.crediya.usecase.user;

import co.com.pragma.crediya.model.user.User;
import co.com.pragma.crediya.model.user.exception.UserNotExistsException;
import co.com.pragma.crediya.model.user.gateways.UserRepository;
import co.com.pragma.crediya.model.user.helper.UserValidator;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class UserUseCase {

    private final UserRepository userRepositoryGateway;

    public Mono<User> saveUser(User user) {
        return Mono.defer(() -> {
            UserValidator.validate(user);
            return userRepositoryGateway.saveUser(user);
        });
    }

    public Mono<User> findUserByDocumentId(String documentId){
        return Mono.defer(() -> userRepositoryGateway.findUserByDocumentId(documentId)
                .switchIfEmpty(Mono.error(new UserNotExistsException(documentId)))
        );
    }
}


