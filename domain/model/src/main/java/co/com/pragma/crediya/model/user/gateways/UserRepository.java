package co.com.pragma.crediya.model.user.gateways;

import co.com.pragma.crediya.model.user.User;
import reactor.core.publisher.Mono;

public interface UserRepository {

    Mono<User> saveUser(User user);

    Mono<Boolean> existsByEmail(String email);
    Mono<Boolean> existsByDocumentId(String documentId);
}
