package co.com.pragma.crediya.api;

import io.swagger.v3.oas.annotations.Operation;
import org.springdoc.core.annotations.RouterOperation;
import org.springdoc.core.annotations.RouterOperations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RouterRest {

    @RouterOperations({
            @RouterOperation(
                    path = "/api/v1/usuarios",
                    method = RequestMethod.POST,
                    beanClass = UserHandler.class,
                    beanMethod = "listenSaveUser",
                    operation = @Operation(
                            summary = "Crear usuario"
                    )
            )
    })

    @Bean
    public RouterFunction<ServerResponse> routerFunction(UserHandler userHandler) {
        return route(POST("/api/v1/usuarios"), userHandler::listenSaveUser);
                //.filter(errorToHttp());

    }

   /* private HandlerFilterFunction<ServerResponse, ServerResponse> errorToHttp() {
        return (request, next) -> next.handle(request)
                .onErrorMap(EmailAlreadyExistsException.class,
                        ex -> new ResponseStatusException(HttpStatus.CONFLICT, ex.getMessage()))
                .onErrorMap(DocumentIdAlreadyExistsException.class,
                        ex -> new ResponseStatusException(HttpStatus.CONFLICT, ex.getMessage()));
    }*/
}
