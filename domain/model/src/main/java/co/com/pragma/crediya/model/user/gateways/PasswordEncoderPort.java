package co.com.pragma.crediya.model.user.gateways;

import reactor.core.publisher.Mono;

public interface PasswordEncoderPort {

    Mono<String> hash(String raw);

    Mono<Boolean> matches(String raw, String hashed);
}
