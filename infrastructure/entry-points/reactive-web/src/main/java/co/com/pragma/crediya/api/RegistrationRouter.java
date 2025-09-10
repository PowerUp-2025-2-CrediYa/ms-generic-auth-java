package co.com.pragma.crediya.api;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RegistrationRouter {

    public static final String BASE_URL = "/api/v1/usuarios";

    @Bean
    public org.springframework.web.reactive.function.server.RouterFunction<?> registrationRoutes(RegistrationHandler handler) {
        return route(POST(BASE_URL), handler::register);
    }
}
