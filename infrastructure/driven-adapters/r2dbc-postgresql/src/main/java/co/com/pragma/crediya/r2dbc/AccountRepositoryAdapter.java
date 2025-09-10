package co.com.pragma.crediya.r2dbc;

import co.com.pragma.crediya.model.user.UserAccount;
import co.com.pragma.crediya.model.user.gateways.AccountRepositoryGateway;
import co.com.pragma.crediya.r2dbc.mappers.AccountDataMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class AccountRepositoryAdapter implements AccountRepositoryGateway {

    private final AccountDataRepository repo;

    @Override
    public Mono<UserAccount> save(UserAccount account) {
        return repo.save(AccountDataMapper.toData(account))
                .map(AccountDataMapper::toDomain);
    }

    @Override
    public Mono<UserAccount> findByUsername(String username) {
        return repo.findByUsername(username)
                .map(AccountDataMapper::toDomain);
    }
}