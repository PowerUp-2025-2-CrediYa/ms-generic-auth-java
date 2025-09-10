package co.com.pragma.crediya.model.user.gateways;

import co.com.pragma.crediya.model.user.UserProfile;
import reactor.core.publisher.Mono;

public interface ProfileRepositoryGateway{

    Mono<UserProfile> save(UserProfile profile);

}
