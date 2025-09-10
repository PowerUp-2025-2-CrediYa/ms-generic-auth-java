package co.com.pragma.crediya.usecase.user;

import co.com.pragma.crediya.model.user.UserAccount;
import co.com.pragma.crediya.model.user.UserProfile;
import co.com.pragma.crediya.model.user.exception.RoleNotExistsException;
import co.com.pragma.crediya.model.user.gateways.*;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
public class RegisterUserUseCase implements RegisterUserPort {

    private final ProfileRepositoryGateway profileRepo;
    private final AccountRepositoryGateway accountRepo;
    private final AccountRoleGateway accountRoleGateway;
    private final RoleGateway roleGateway;
    private final PasswordEncoderPort passwordEncoder;


    /**
     * Orquesta el registro de usuario:
     * - Valida roles
     * - Hashea password
     * - Persiste Profile y Account
     * - Sincroniza roles de la cuenta
     * Devuelve ambos objetos de dominio ya persistidos.
     */
    public Mono<RegistrationOutcome> register(UserProfile profile,
                                              String usernameEmail,
                                              String rawPassword,
                                              List<String> roleCodes) {
        return Mono.defer(() -> {
            // Validaciones mínimas (puedes mover a un validador de dominio)
            if (profile == null) return Mono.error(new IllegalArgumentException("profile requerido"));
            if (usernameEmail == null || usernameEmail.isBlank()) return Mono.error(new IllegalArgumentException("username/email requerido"));
            if (rawPassword == null || rawPassword.isBlank()) return Mono.error(new IllegalArgumentException("password requerido"));
            if (roleCodes == null || roleCodes.isEmpty()) return Mono.error(new IllegalArgumentException("roles requeridos"));

            var normalizedProfile = profile.toBuilder().email(usernameEmail.trim()).build();

            Set<String> requested = new HashSet<>(roleCodes);
            return roleGateway.findExistingCodes(requested)
                    .flatMap(existing -> {
                        Set<String> missing = new HashSet<>(requested);
                        missing.removeAll(existing);
                        if (!missing.isEmpty()) {
                            return Mono.error(new RoleNotExistsException(String.join(",", missing)));
                        }

                        return passwordEncoder.hash(rawPassword)
                                .flatMap(hashed ->
                                        profileRepo.save(normalizedProfile)
                                                .flatMap(savedProfile -> {
                                                    var account = UserAccount.builder()
                                                            .profileId(savedProfile.getId())
                                                            .username(usernameEmail.trim())
                                                            .hashedPassword(hashed)
                                                            .roles(List.copyOf(roleCodes))
                                                            .build();

                                                    return accountRepo.save(account)
                                                            .flatMap(savedAcc ->
                                                                    accountRoleGateway.replaceAccountRoles(savedAcc.getId(), account.getRoles())
                                                                            .then(Mono.just(new RegistrationOutcome(savedProfile, savedAcc)))
                                                            );
                                                })
                                );
                    });
        });
    }

    /** Resultado del caso de uso: modelos de dominio ya persistidos. */
    public record RegistrationOutcome(UserProfile profile, UserAccount account) {}
}
