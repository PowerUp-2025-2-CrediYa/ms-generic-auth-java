package co.com.pragma.crediya.r2dbc;

import co.com.pragma.crediya.r2dbc.entity.RoleEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

import java.util.Collection;

public interface RolesReactiveRepository extends ReactiveCrudRepository<RoleEntity, Integer> {

    Flux<RoleEntity> findByCodeIn(Collection<String> codes);
}
