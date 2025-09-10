package co.com.pragma.crediya.config;

import co.com.pragma.crediya.model.user.gateways.*;
import co.com.pragma.crediya.usecase.user.RegisterUserUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
@ComponentScan(basePackages = "co.com.pragma.crediya.usecase",
        includeFilters = {
                @ComponentScan.Filter(type = FilterType.REGEX, pattern = "^.+UseCase$")
        },
        useDefaultFilters = false)
public class UseCasesConfig {

    @Bean
    public RegisterUserUseCase registerUserUseCase(ProfileRepositoryGateway profileRepo,
                                                   AccountRepositoryGateway accountRepo,
                                                   AccountRoleGateway accountRoleGateway,
                                                   RoleGateway roleGateway,
                                                   PasswordEncoderPort passwordEncoder) {
        return new RegisterUserUseCase(profileRepo, accountRepo, accountRoleGateway, roleGateway, passwordEncoder);
    }
}
