package co.com.pragma.crediya.r2dbc.config;

import org.junit.jupiter.api.Test;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.context.annotation.Configuration;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

class PostgresqlConnectionPropertiesTest {

    private final ApplicationContextRunner contextRunner =
            new ApplicationContextRunner()
                    .withUserConfiguration(BindConfig.class)
                    .withPropertyValues(
                            "adapters.r2dbc.host=localhost",
                            "adapters.r2dbc.port=5432",
                            "adapters.r2dbc.database=mydb",
                            "adapters.r2dbc.schema=public",
                            "adapters.r2dbc.username=myuser",
                            "adapters.r2dbc.password=secret"
                    );

    @Configuration
    @EnableConfigurationProperties(PostgresqlConnectionProperties.class)
    static class BindConfig { }

    @Test
    void bindsAllProperties() {
        contextRunner.run(ctx -> {
            assertThat(ctx).hasSingleBean(PostgresqlConnectionProperties.class);
            PostgresqlConnectionProperties props = ctx.getBean(PostgresqlConnectionProperties.class);

            assertThat(props.host()).isEqualTo("localhost");
            assertThat(props.port()).isEqualTo(5432);
            assertThat(props.database()).isEqualTo("mydb");
            assertThat(props.schema()).isEqualTo("public");
            assertThat(props.username()).isEqualTo("myuser");
            assertThat(props.password()).isEqualTo("secret");
        });
    }

    @Test
    void missingPropertiesBindAsNull() {
        new ApplicationContextRunner()
                .withUserConfiguration(BindConfig.class)
                .withPropertyValues(
                        "adapters.r2dbc.host=localhost" // solo una propiedad
                )
                .run(ctx -> {
                    PostgresqlConnectionProperties props = ctx.getBean(PostgresqlConnectionProperties.class);
                    assertThat(props.host()).isEqualTo("localhost");
                    assertThat(props.port()).isNull();
                    assertThat(props.database()).isNull();
                    assertThat(props.schema()).isNull();
                    assertThat(props.username()).isNull();
                    assertThat(props.password()).isNull();
                });
    }
}
