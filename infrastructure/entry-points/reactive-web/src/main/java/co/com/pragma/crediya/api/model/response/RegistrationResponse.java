package co.com.pragma.crediya.api.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Respuesta al crear un usuario")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RegistrationResponse {

    private ProfileResponse profile;
    private AccountResponse account;
}
