package co.com.pragma.crediya.r2dbc.helper;

import lombok.RequiredArgsConstructor;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class AccountRoleQueries {

    private final DatabaseClient client;

    public Flux<String> findRoleCodesByAccountId(UUID accountId) {
        String sql = """
            SELECT r.code
            FROM account_roles ar
            JOIN roles r ON r.id_rol = ar.role_id
            WHERE ar.account_id = :acc
            """;
        return client.sql(sql)
                .bind("acc", accountId)
                .map(row -> row.get("code", String.class))
                .all();
    }

    public Mono<Void> replaceAccountRoles(UUID accountId, List<String> roleCodes) {
        String deleteSql = "DELETE FROM account_roles WHERE account_id = :acc";
        String insertSql = """
            INSERT INTO account_roles(account_id, role_id)
            SELECT :acc, r.id_rol FROM roles r WHERE r.code = :code
            """;
        return client.sql(deleteSql).bind("acc", accountId).then()
                .thenMany(Flux.fromIterable(roleCodes)
                        .flatMap(code -> client.sql(insertSql)
                                .bind("acc", accountId)
                                .bind("code", code)
                                .then()))
                .then();
    }

}
