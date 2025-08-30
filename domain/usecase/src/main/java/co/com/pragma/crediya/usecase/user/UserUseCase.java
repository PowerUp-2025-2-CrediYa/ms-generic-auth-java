package co.com.pragma.crediya.usecase.user;

import co.com.pragma.crediya.model.user.User;
import co.com.pragma.crediya.model.user.exception.DocumentIdAlreadyExistsException;
import co.com.pragma.crediya.model.user.exception.EmailAlreadyExistsException;
import co.com.pragma.crediya.model.user.gateways.UserRepository;
import co.com.pragma.crediya.model.user.validation.UserValidator;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class UserUseCase {

    private final UserRepository userRepositoryGateway;

    public Mono<User> save(User user) {

        return Mono.defer(() -> {
            UserValidator.validate(user);
            return Mono.zip(
                    userRepositoryGateway.existsByEmail(user.getEmail()),
                    userRepositoryGateway.existsByDocumentId(user.getDocumentId())
            ).flatMap(t -> {
                boolean emailExists = t.getT1();
                boolean docExists   = t.getT2();
                if (emailExists) return Mono.error(new EmailAlreadyExistsException(user.getEmail()));
                if (docExists)   return Mono.error(new DocumentIdAlreadyExistsException(user.getDocumentId()));
                return userRepositoryGateway.saveUser(user);
            });
        });
    }

}


