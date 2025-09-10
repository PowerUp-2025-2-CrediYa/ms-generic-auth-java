package co.com.pragma.crediya.r2dbc.tx;

import co.com.pragma.crediya.model.user.UserProfile;
import co.com.pragma.crediya.usecase.user.RegisterUserPort;
import co.com.pragma.crediya.usecase.user.RegisterUserUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.springframework.transaction.reactive.TransactionalOperator;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
@Primary // <- para que Spring prefiera este bean al inyectar RegisterUserPort
@RequiredArgsConstructor
public class TransactionalRegisterUser implements RegisterUserPort {

    private final RegisterUserUseCase useCase;   // el caso de uso puro
    private final TransactionalOperator tx;      // solo existe en este módulo

    @Override
    public Mono<RegisterUserUseCase.RegistrationOutcome> register(
            UserProfile profile, String username, String rawPassword, List<String> roles) {
        return tx.transactional(useCase.register(profile, username, rawPassword, roles));
    }
}
