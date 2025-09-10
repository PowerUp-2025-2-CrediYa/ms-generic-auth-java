package co.com.pragma.crediya.r2dbc;

import co.com.pragma.crediya.model.user.UserProfile;
import co.com.pragma.crediya.model.user.gateways.ProfileRepositoryGateway;
import co.com.pragma.crediya.r2dbc.mappers.ProfileEntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component // 👈 necesario para que Spring lo registre como bean
@RequiredArgsConstructor
public class ProfileRepositoryAdapter implements ProfileRepositoryGateway {

    private final ProfileDataRepository repo;

    @Override
    public Mono<UserProfile> save(UserProfile profile) {
        return repo.save(ProfileEntityMapper.toData(profile))
                .map(ProfileEntityMapper::toDomain);
    }
}