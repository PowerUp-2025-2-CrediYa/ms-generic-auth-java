package co.com.pragma.crediya.model.user.gateways;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;

public interface AccountRoleGateway {

    Mono<Void> replaceAccountRoles(UUID accountId, List<String> roleCodes);
    Flux<String> findRoleCodesByAccountId(UUID accountId); // útil para lecturas
}
