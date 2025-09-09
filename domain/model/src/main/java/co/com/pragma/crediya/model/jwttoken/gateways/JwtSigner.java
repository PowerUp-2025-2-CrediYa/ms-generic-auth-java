package co.com.pragma.crediya.model.jwttoken.gateways;

import co.com.pragma.crediya.model.jwttoken.JwtToken;
import co.com.pragma.crediya.model.user.User;
import reactor.core.publisher.Mono;

public interface JwtSigner {

    Mono<JwtToken> sign(User user);

}
