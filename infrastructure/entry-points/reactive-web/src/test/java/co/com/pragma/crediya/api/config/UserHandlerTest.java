package co.com.pragma.crediya.api.config;

import co.com.pragma.crediya.api.UserHandler;
import co.com.pragma.crediya.api.exceptions.GlobalErrorAttributes;
import co.com.pragma.crediya.api.exceptions.GlobalExceptionHandler;
import co.com.pragma.crediya.api.helper.ExceptionHelper;
import co.com.pragma.crediya.model.user.User;
import co.com.pragma.crediya.model.user.exception.EmailAlreadyExistsException;
import co.com.pragma.crediya.model.user.exception.InvalidBaseSalaryRangeException;
import co.com.pragma.crediya.usecase.user.UserUseCase;
import com.pragma.observability.AppLogger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@WebFluxTest(
)
@Import({
        UserHandlerWebTest.TestRoutes.class,
        UserHandlerWebTest.TestMocks.class,
        GlobalExceptionHandler.class,
        GlobalErrorAttributes.class
})
class UserHandlerWebTest {

    @Autowired
    WebTestClient client;

    @Autowired
    UserUseCase userUseCase;

    @Autowired
    AppLogger appLogger;

    @BeforeEach
    void resetMocks() {
        Mockito.reset(userUseCase, appLogger);
    }

    @Test
    void createUser_withValidData_returns201Created() {
        String payload = """
            {"email":"nuevo@dom.com","documentId":"D1","firstName":"Carlos","baseSalary":1000000}
        """;

        User saved = new User();
        saved.setId(UUID.randomUUID());
        saved.setEmail("nuevo@dom.com");
        saved.setDocumentId("D1");

        when(userUseCase.save(any())).thenReturn(Mono.just(saved));

        client.post().uri("/api/v1/usuarios")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(payload)
                .exchange()
                .expectStatus().isCreated()
                .expectHeader().contentTypeCompatibleWith(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.email").isEqualTo("nuevo@dom.com")
                .jsonPath("$.documentId").isEqualTo("D1");
    }

    @Test
    void createUser_withExistingUser_returns409Conflict() {
        String payload = """
            {"email":"dup@dom.com","documentId":"D2","firstName":"Carlos","baseSalary":1000000}
        """;

        when(userUseCase.save(any()))
                .thenReturn(Mono.error(new EmailAlreadyExistsException("dup@dom.com")));

        client.post().uri("/api/v1/usuarios")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(payload)
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.CONFLICT)
                .expectHeader().contentTypeCompatibleWith(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.status").isEqualTo(409)
                .jsonPath("$.error").exists()
                .jsonPath("$.message").exists()
                .jsonPath("$.path").isEqualTo("/api/v1/usuarios");
    }

    @Test
    void createUser_whenSalaryIsUnderage_returns422UnprocessableEntit() {

        String payload = """
            {"firstName":"Jon", "lastName":"Doe", "email":"ok@dom.com","documentId":"123456789","phoneNumber":"1234568910", "baseSalary":-1, "roleId":"1"}
        """;

        when(userUseCase.save(any()))
                .thenReturn(Mono.error(new InvalidBaseSalaryRangeException("El salario base debe ser >= 0")));

        client.post().uri("/api/v1/usuarios")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(payload)
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY)
                .expectHeader().contentTypeCompatibleWith(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.status").isEqualTo(422)
                .jsonPath("$.message").exists()
                .jsonPath("$.path").isEqualTo("/api/v1/usuarios");

    }

    /** Router de prueba minimal para el slice */
    @TestConfiguration
    static class TestRoutes {
        @Bean
        RouterFunction<ServerResponse> routes(UserHandler handler) {
            return route(POST("/api/v1/usuarios"), handler::listenSaveUser);
        }
    }

    @TestConfiguration
    static class TestMocks {
        @Bean
        UserUseCase userUseCase() { return Mockito.mock(UserUseCase.class); }

        @Bean
        AppLogger appLogger() { return Mockito.mock(AppLogger.class); }

        // ⬇️ NUEVO: registra el helper que requiere tu GlobalExceptionHandler
        @Bean
        ExceptionHelper exceptionHelper() { return new ExceptionHelper(); }

        @Bean
        UserHandler userHandler(UserUseCase uc, AppLogger log) {
            return new UserHandler(uc, log);
        }
    }

    /** Mini Boot config para que @WebFluxTest tenga un @SpringBootConfiguration */
    @SpringBootConfiguration
    @EnableAutoConfiguration
    static class BootTestConfig { }
}