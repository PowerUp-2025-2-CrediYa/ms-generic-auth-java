package co.com.pragma.crediya.api;

import co.com.pragma.crediya.api.model.request.UserRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RouterRestTest {

    @Mock
    private UserHandler userHandler;

    private RouterFunction<ServerResponse> routerFunction;

    private WebTestClient webTestClient;

    @BeforeEach
    void setUp() {

        routerFunction = new RouterRest().routerFunction(userHandler);
        webTestClient = WebTestClient.bindToRouterFunction(routerFunction).build();
    }

    @Test
    void shouldRoutePostToListenSaveUser() {

        UserRequest userRequest = UserRequest.builder()
                .firstName("Jon")
                .lastName("Doe")
                .email("ok@dom.com")
                .documentId("123456789")
                .phoneNumber("+1234568910")
                .roleId(1)
                .baseSalary(5000000.0)
                .build();

        ServerResponse mockResponse = ServerResponse.ok().bodyValue("Usuario creado").block();
        when(userHandler.listenSaveUser(any())).thenReturn(Mono.just(mockResponse));

        webTestClient.post()
                .uri("/api/v1/usuarios")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(userRequest)
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class).isEqualTo("Usuario creado");

        verify(userHandler).listenSaveUser(any());

    }
}
