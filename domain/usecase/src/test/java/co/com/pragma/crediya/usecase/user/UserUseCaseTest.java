package co.com.pragma.crediya.usecase.user;

import co.com.pragma.crediya.model.user.User;
import co.com.pragma.crediya.model.user.exception.InvalidBaseSalaryRangeException;
import co.com.pragma.crediya.model.user.gateways.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserUseCaseTest{

    @Mock
    UserRepository userRepositoryGateway;

    UserUseCase useCase;

    @BeforeEach
    void setUp() {
        useCase = new UserUseCase(userRepositoryGateway);
    }

    @Test
    void save_whenValidUser_savesAndReturns() {

        User user = UserValidator.valid();
        when(userRepositoryGateway.saveUser(user)).thenReturn(Mono.just(user));

        StepVerifier.create(useCase.save(user))
                .expectNext(user)
                .verifyComplete();

        verify(userRepositoryGateway).saveUser(user);
    }

    @Test
    void save_invalidBaseSalary_throwsInvalidBaseSalaryRange() {

        User invalid = UserValidator.withBaseSalary("-1");

        StepVerifier.create(useCase.save(invalid))
                .expectError(InvalidBaseSalaryRangeException.class)
                .verify();

        verifyNoInteractions(userRepositoryGateway);
    }


    @Test
    void save_WhenRepositoryFails_ThrowsException() {

        User user = UserValidator.valid();
        RuntimeException boom = new RuntimeException("DB down");
        when(userRepositoryGateway.saveUser(any())).thenReturn(Mono.error(boom));

        StepVerifier.create(useCase.save(user))
                .expectErrorMatches(ex -> ex == boom)
                .verify();

        verify(userRepositoryGateway).saveUser(user);
    }

    @Test
    void save_isLazy_DoesNotCallRepoUntilSubscribed() {

        User user = UserValidator.valid();
        when(userRepositoryGateway.saveUser(user)).thenReturn(Mono.just(user));

        verifyNoInteractions(userRepositoryGateway);

        StepVerifier.create(useCase.save(user))
                .expectNext(user)
                .verifyComplete();

        verify(userRepositoryGateway).saveUser(user);
    }

}
