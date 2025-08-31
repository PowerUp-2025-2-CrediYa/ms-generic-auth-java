package co.com.pragma.crediya.usecase.user;

import co.com.pragma.crediya.model.user.User;
import co.com.pragma.crediya.model.user.gateways.UserRepository;
import co.com.pragma.crediya.model.user.helper.UserValidator;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class UserUseCase {

    private final UserRepository userRepositoryGateway;

    public Mono<User> save(User user) {
        return Mono.defer(() -> {
            UserValidator.validate(user);
            return userRepositoryGateway.saveUser(user);
        });
    }
}


