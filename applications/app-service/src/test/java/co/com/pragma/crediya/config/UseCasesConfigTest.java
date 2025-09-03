package co.com.pragma.crediya.config;

import co.com.pragma.crediya.model.user.gateways.UserRepository;
import co.com.pragma.crediya.usecase.user.UserUseCase;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class UseCasesConfigTest {

    @Test
    void testUseCaseBeansExist() {
        try (AnnotationConfigApplicationContext ctx =
                     new AnnotationConfigApplicationContext(TestConfig.class)) {

            String[] names = ctx.getBeanNamesForType(UserUseCase.class);
            assertThat(names)
                    .as("Debe existir al menos un bean de tipo UserUseCase")
                    .isNotEmpty();

            UserUseCase useCase = ctx.getBean(UserUseCase.class);
            assertThat(useCase).isNotNull();
        }
    }

    @Configuration
    @Import(UseCasesConfig.class)
    static class TestConfig {
        @Bean
        public UserRepository userRepository() {

            return Mockito.mock(UserRepository.class);
        }

    }
}