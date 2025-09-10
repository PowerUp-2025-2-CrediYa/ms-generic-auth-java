package co.com.pragma.crediya.r2dbc;

import co.com.pragma.crediya.model.user.gateways.RoleGateway;
import co.com.pragma.crediya.r2dbc.entity.RoleEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class RolesRepositoryAdapter implements RoleGateway {

    private final DatabaseClient client;
    private final RolesReactiveRepository roleRepo;

    @Override
    public Flux<String> findRoleCodesByUserId(UUID userId) {
        String sql = """
                  SELECT r.code
                  FROM auth.usuarios_roles ur   
                  JOIN auth.roles r ON r.id_rol = ur.id_rol
                  WHERE ur.id_usuario = :userId
                """;
        return client.sql(sql)
                .bind("userId", userId)
                .map(row -> row.get("code", String.class))
                .all();
    }

    @Override
    public Mono<Void> replaceUserRoles(UUID userId, List<String> roleCodes) {
        String deleteSql = "DELETE FROM auth.usuarios_roles WHERE id_usuario = :userId";
        String insertSql = """
                  INSERT INTO auth.usuarios_roles(id_usuario, id_rol)
                  SELECT :userId, r.id_rol FROM auth.roles r WHERE r.code = :code
                """;
        return client.sql(deleteSql).bind("userId", userId).then()
                .thenMany(Flux.fromIterable(roleCodes)
                        .flatMap(code -> client.sql(insertSql)
                                .bind("userId", userId)
                                .bind("code", code)
                                .then()))
                .then();
    }

    @Override
    public Mono<Set<String>> findExistingCodes(Set<String> codes) {
        if (codes == null || codes.isEmpty()) {
            return Mono.just(Set.of());
        }
        return roleRepo.findByCodeIn(codes)
                .map(RoleEntity::getCode)
                .collectList()
                .map(HashSet::new);
    }
}
