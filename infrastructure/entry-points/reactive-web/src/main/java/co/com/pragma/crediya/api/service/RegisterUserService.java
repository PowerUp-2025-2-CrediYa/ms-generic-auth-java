package co.com.pragma.crediya.api.service;

import co.com.pragma.crediya.model.user.UserProfile;
import co.com.pragma.crediya.usecase.user.RegisterUserPort;
import co.com.pragma.crediya.usecase.user.RegisterUserUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RegisterUserService {

    private final RegisterUserPort port; // <- interfaz; Spring inyecta el decorator @Primary

    public Mono<RegisterUserUseCase.RegistrationOutcome> register(
            UserProfile profile, String username, String rawPassword, List<String> roles) {
        return port.register(profile, username, rawPassword, roles);
    }
}
