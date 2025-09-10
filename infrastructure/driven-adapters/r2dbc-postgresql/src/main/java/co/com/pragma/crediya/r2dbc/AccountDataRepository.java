package co.com.pragma.crediya.r2dbc;

import co.com.pragma.crediya.r2dbc.entity.AccountEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface AccountDataRepository extends ReactiveCrudRepository<AccountEntity, UUID> {
    Mono<AccountEntity> findByUsername(String username);
}