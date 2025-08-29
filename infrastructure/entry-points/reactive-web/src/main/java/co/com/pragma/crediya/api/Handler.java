package co.com.pragma.crediya.api;

import co.com.pragma.crediya.api.mappers.UserMapper;
import co.com.pragma.crediya.api.mappers.UserResponseMapper;
import co.com.pragma.crediya.api.model.request.UserRequest;
import co.com.pragma.crediya.api.model.response.UserResponse;
import co.com.pragma.crediya.usecase.user.UserUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;


@Component
@RequiredArgsConstructor
@Tag(name = "Gestión de Usuarios", description = "Operaciones relacionadas con usuarios")
public class Handler {

    private final UserUseCase userUseCase;

    @Operation(
            summary = "Crear usuario",
            description = "Recibe un usuario y lo guarda en el sistema",
            requestBody = @RequestBody(
                    required = true,
                    content = @Content(schema = @Schema(implementation = UserRequest.class))
            ),
            responses = {
                    @ApiResponse(responseCode = "201", description = "Usuario creado exitosamente",
                            content = @Content(schema = @Schema(implementation = UserResponse.class))),
                    @ApiResponse(responseCode = "400", description = "Datos inválidos"),
                    @ApiResponse(responseCode = "422", description = "Violación de reglas de negocio"),
                    @ApiResponse(responseCode = "409", description = "Usuario ya existe")
            }
    )


    public Mono<ServerResponse> listenSaveUser(ServerRequest serverRequest) {

        return serverRequest.bodyToMono(UserRequest.class)
                .map(UserMapper::toDomain)
                .flatMap(userUseCase::save)
                .flatMap(savedUser -> ServerResponse
                        .status(HttpStatus.CREATED)
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(UserResponseMapper.fromDomain(savedUser)));

    }
}
