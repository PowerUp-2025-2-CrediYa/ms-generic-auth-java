package co.com.pragma.crediya.r2dbc;

import co.com.pragma.crediya.model.user.gateways.AccountRoleGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class AccountRoleRepositoryAdapter implements AccountRoleGateway {

    private final DatabaseClient client;

    @Override
    public Mono<Void> replaceAccountRoles(UUID accountId, List<String> roleCodes) {
        String deleteSql = "DELETE FROM auth.account_roles WHERE account_id = :acc";

        // Insert por code (JOIN implícito por sub-select):
        String insertSql = """
            INSERT INTO auth.account_roles(account_id, role_id)
            SELECT :acc, r.id_rol
            FROM auth.roles r
            WHERE r.code = :code
            """;

        return client.sql(deleteSql)
                .bind("acc", accountId)
                .then()
                .thenMany(Flux.fromIterable(roleCodes)
                        .flatMap(code -> client.sql(insertSql)
                                .bind("acc", accountId)
                                .bind("code", code)
                                .then()))
                .then();
    }

    @Override
    public Flux<String> findRoleCodesByAccountId(UUID accountId) {
        String sql = """
            SELECT r.code
            FROM auth.account_roles ar
            JOIN auth.roles r ON r.id_rol = ar.role_id
            WHERE ar.account_id = :acc
            """;
        return client.sql(sql)
                .bind("acc", accountId)
                .map(row -> row.get("code", String.class))
                .all();
    }
}
