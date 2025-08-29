package co.com.pragma.crediya.api;

import co.com.pragma.crediya.api.mappers.UserMapper;
import co.com.pragma.crediya.api.model.request.UserRequest;
import co.com.pragma.crediya.model.user.User;
import co.com.pragma.crediya.usecase.user.UserUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.RouterOperation;
import org.springdoc.core.annotations.RouterOperations;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class Handler {

    private  final UserUseCase userUseCase;

    public Mono<ServerResponse> listenGETUseCase(ServerRequest serverRequest) {
        // useCase.logic();
        return ServerResponse.ok().bodyValue("");
    }

    public Mono<ServerResponse> listenGETOtherUseCase(ServerRequest serverRequest) {
        // useCase2.logic();
        return ServerResponse.ok().bodyValue("");
    }

        public Mono<ServerResponse> listenSaveUser(ServerRequest serverRequest) {
        // useCase.logic();
        return serverRequest.bodyToMono(UserRequest.class)
                .map(UserMapper::toDomain)
                .flatMap(userUseCase::save)
                .flatMap(savedUser -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(savedUser));
    }
}
