package co.com.pragma.crediya.usecase.user;

import co.com.pragma.crediya.model.user.User;
import co.com.pragma.crediya.model.user.exception.DocumentIdAlreadyExistsException;
import co.com.pragma.crediya.model.user.exception.EmailAlreadyExistsException;
import co.com.pragma.crediya.model.user.exception.InvalidUserException;
import co.com.pragma.crediya.model.user.gateways.UserRepository;
import co.com.pragma.crediya.model.user.validation.UserValidator;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class UserUseCase {

    private final UserRepository userRepositoryGateway;

    public Mono<User> save(User user) {

        try {
            UserValidator.validate(user);
        } catch (InvalidUserException e) {
            return Mono.error(e);
        }


        return userRepositoryGateway.findUserByEmail(user.getEmail())
                .flatMap(existing -> Mono.<User>error(new EmailAlreadyExistsException(user.getEmail())))
                .switchIfEmpty(
                        userRepositoryGateway.findUserByDocumentId(user.getDocumentId())
                                .flatMap(existing -> Mono.<User>error(new DocumentIdAlreadyExistsException(user.getDocumentId())))
                                .switchIfEmpty(userRepositoryGateway.saveUser(user))
                );
    }


}


