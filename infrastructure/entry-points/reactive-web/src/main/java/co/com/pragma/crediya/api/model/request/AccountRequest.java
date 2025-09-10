package co.com.pragma.crediya.api.model.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Modelo de entrada para la cuenta del usuario")
public class AccountRequest {
    @Schema(description = "Correo electrónico válido", example = "carlos@example.com")
    private String username;

    @Schema(description = "Password de la cuenta", example = "carlos@example.com")
    private String password;

    @Schema(description = "Códigos de rol (ROLE_*)", example = "[\"ROLE_ADMINISTRADOR\",\"ROLE_ASESOR\"]")
    private List<String> roles;

}
