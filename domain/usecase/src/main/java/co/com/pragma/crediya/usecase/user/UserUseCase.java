package co.com.pragma.crediya.usecase.user;

import co.com.pragma.crediya.model.user.User;
import co.com.pragma.crediya.model.user.exception.RoleNotExistsException;
import co.com.pragma.crediya.model.user.exception.UserNotExistsException;
import co.com.pragma.crediya.model.user.gateways.RoleRepository;
import co.com.pragma.crediya.model.user.gateways.UserRepository;
import co.com.pragma.crediya.model.user.helper.UserValidator;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.util.HashSet;
import java.util.Set;

@RequiredArgsConstructor
public class UserUseCase {

    private final UserRepository userRepositoryGateway;
    private final RoleRepository roleRepositoryGateway;

    public Mono<User> saveUser(User user) {

        return Mono.defer(() -> {
            UserValidator.validate(user);
            Set<String> requested = new HashSet<>(user.getRoles() != null ? user.getRoles() : Set.of());

            return roleRepositoryGateway.findExistingCodes(requested)
                    .flatMap(existing -> {
                        Set<String> missing = new HashSet<>(requested);
                        missing.removeAll(existing);

                        if (!missing.isEmpty()) {
                            return Mono.error(new RoleNotExistsException(String.join(",", missing)));
                        }

                        return userRepositoryGateway.saveUser(user);
                    });
        });
    }

    public Mono<User> findUserByDocumentId(String documentId) {
        return Mono.defer(() -> userRepositoryGateway.findUserByDocumentId(documentId)
                .switchIfEmpty(Mono.error(new UserNotExistsException(documentId)))
        );
    }
}


