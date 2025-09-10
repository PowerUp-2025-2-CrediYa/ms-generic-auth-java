package co.com.pragma.crediya.api;

import co.com.pragma.crediya.api.mappers.RegistrationMapper;
import co.com.pragma.crediya.api.mappers.RegistrationResponseMapper;
import co.com.pragma.crediya.api.model.request.RegistrationRequest;
import co.com.pragma.crediya.api.model.response.RegistrationResponse;
import co.com.pragma.crediya.api.service.RegisterUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class RegistrationHandler {
    private final RegistrationMapper mapper;
    private final RegistrationResponseMapper responseMapper;
    private final RegisterUserService service;

    public Mono<ServerResponse> register(ServerRequest request) {
        return request.bodyToMono(RegistrationRequest.class)
                .flatMap(req -> {
                    var email = req.getAccount().getUsername();
                    var profileDomain = mapper.toProfile(req.getProfile(), email);

                    return service.register(
                            profileDomain,
                            req.getAccount().getUsername(),
                            req.getAccount().getPassword(),
                            req.getAccount().getRoles()
                    ).map(outcome -> {
                        // outcome trae UserProfile y Account persistidos
                        RegistrationResponse body = responseMapper.toResponse(outcome.profile(), outcome.account());
                        return body;
                    });
                })
                .flatMap(body -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(body));
    }

}


