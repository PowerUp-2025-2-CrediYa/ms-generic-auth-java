package co.com.pragma.crediya.api;

import co.com.pragma.crediya.api.mappers.UserMapper;
import co.com.pragma.crediya.api.mappers.UserResponseMapper;
import co.com.pragma.crediya.api.model.request.UserRequest;
import co.com.pragma.crediya.api.model.response.ApiError;
import co.com.pragma.crediya.api.model.response.UserResponse;
import co.com.pragma.crediya.usecase.user.UserUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
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
public class UserHandler {

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
                    @ApiResponse(responseCode = "400", description = "Datos inválidos",
                            content = @Content(schema = @Schema(implementation = ApiError.class),
                                    examples = @ExampleObject(
                                            value = """
                                                    {
                                                      "error": "Bad Request",
                                                      "message": "El nombre no puede estar vacío",
                                                      "path": "/api/v1/usuarios",
                                                      "status": 400,
                                                      "timestamp": "2025-08-30T02:39:08.855306500Z"
                                                    }
                                                    """
                                    ))),
                    @ApiResponse(responseCode = "422", description = "Violación de reglas de negocio",
                            content = @Content(schema = @Schema(implementation = ApiError.class),
                                    examples = @ExampleObject(
                                            value = """
                                                    {
                                                      "error": "Bad request.",
                                                      "message": "El salario base debe estar entre 0 y 15.000.000",
                                                      "path": "/api/v1/usuarios",
                                                      "status": 422,
                                                      "timestamp": "2025-08-30T02:41:02.238173500Z"
                                                    }
                                                    """
                                    ))),
                    @ApiResponse(responseCode = "409", description = "Usuario ya existe",
                            content = @Content(schema = @Schema(implementation = ApiError.class),
                                    examples = @ExampleObject(
                                            value = """
                                                    {
                                                      "error": "Conflict",
                                                      "message": "Ya existe un usuario con el email: xxxxxx@xxxxx.com",
                                                      "path": "/api/v1/usuarios",
                                                      "status": 409,
                                                      "timestamp": "2025-08-30T02:24:46.819150400Z"
                                                    }
                                                    """
                                    )))
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
