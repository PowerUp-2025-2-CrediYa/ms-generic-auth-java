package co.com.pragma.crediya.model.jwttoken.gateways;

import co.com.pragma.crediya.model.jwttoken.JwtClaims;
import reactor.core.publisher.Mono;

public interface JwtVerifier {

    Mono<JwtClaims> verify(String token);
}
