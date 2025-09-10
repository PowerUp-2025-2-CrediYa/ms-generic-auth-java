package co.com.pragma.crediya.model.user.gateways;

import co.com.pragma.crediya.model.user.UserAccount;
import reactor.core.publisher.Mono;

public interface AccountRepositoryGateway{
    Mono<UserAccount> save(UserAccount account);
    Mono<UserAccount> findByUsername(String username); // opcional pero útil para login
}
