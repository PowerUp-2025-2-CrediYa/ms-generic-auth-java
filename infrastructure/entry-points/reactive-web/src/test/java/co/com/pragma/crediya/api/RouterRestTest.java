package co.com.pragma.crediya.api;

import co.com.pragma.crediya.api.mappers.UserResponseMapper;
import co.com.pragma.crediya.api.model.request.UserRequest;
import co.com.pragma.crediya.api.model.response.UserResponse;
import co.com.pragma.crediya.model.user.User;
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

import static co.com.pragma.crediya.api.config.UtilUserTesting.*;
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
        UserRequest userRequest = getUserRequest();

        ServerResponse mockResponse = ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue("Usuario creado")
                .block();

        when(userHandler.listenSaveUser(any())).thenReturn(Mono.just(mockResponse));

        webTestClient.post()
                .uri("/api/v1/usuarios")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(userRequest)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentTypeCompatibleWith(MediaType.APPLICATION_JSON)
                .expectBody(String.class).isEqualTo("Usuario creado");

        verify(userHandler).listenSaveUser(any());
    }

    @Test
    void whenUserExists_thenReturns200WithUserResponse() {
        String documentId = "123456789";
        User mockUser = getUser();

        UserResponse expectedResponse = UserResponseMapper.fromDomain(mockUser);
        ServerResponse mockResponse = ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(expectedResponse)
                .block();

        when(userHandler.listenFindUserByDocumentId(any())).thenReturn(Mono.just(mockResponse));

        webTestClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(BASE_URL)
                        .queryParam("documentId", documentId)
                        .build())
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentTypeCompatibleWith(MediaType.APPLICATION_JSON)
                .expectBody(UserResponse.class);

        verify(userHandler).listenFindUserByDocumentId(any());
    }
}
