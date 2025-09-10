package co.com.pragma.crediya.api.model.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Modelo de entrada para el perfil y cuenta del usuario")
public class RegistrationRequest {

    private ProfileRequest profile;
    private AccountRequest account;
}
