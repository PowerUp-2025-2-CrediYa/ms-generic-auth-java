package co.com.pragma.crediya.model.user.gateways;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface RoleGateway {

    Flux<String> findRoleCodesByUserId(UUID userId);

    Mono<Void> replaceUserRoles(UUID userId, List<String> roleCodes);

    Mono<Set<String>> findExistingCodes(Set<String> codes);
}
