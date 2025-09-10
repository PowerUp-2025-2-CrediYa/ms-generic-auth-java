package co.com.pragma.crediya.passwordencoder;

import co.com.pragma.crediya.model.user.gateways.PasswordEncoderPort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class BCryptPasswordEncoderAdapter  implements PasswordEncoderPort {

    private final BCryptPasswordEncoder delegate = new BCryptPasswordEncoder(12); // fuerza 12

    @Override
    public Mono<String> hash(String raw) {
        return Mono.fromSupplier(() -> delegate.encode(raw));
    }

    @Override
    public Mono<Boolean> matches(String raw, String hashed) {
        return Mono.fromSupplier(() -> delegate.matches(raw, hashed));
    }
}