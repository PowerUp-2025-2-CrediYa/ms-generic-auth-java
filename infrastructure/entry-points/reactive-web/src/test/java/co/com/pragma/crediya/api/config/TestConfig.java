package co.com.pragma.crediya.api.config;

import co.com.pragma.crediya.usecase.user.UserUseCase;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TestConfig {

    @Bean
    public UserUseCase userUseCase() {
        return Mockito.mock(UserUseCase.class);
    }

}
