package co.com.pragma.crediya.usecase.user;

import co.com.pragma.crediya.model.user.UserProfile;
import reactor.core.publisher.Mono;

import java.util.List;

public interface RegisterUserPort {

    Mono<RegisterUserUseCase.RegistrationOutcome> register(
            UserProfile profile, String username, String rawPassword, List<String> roles
    );
}
