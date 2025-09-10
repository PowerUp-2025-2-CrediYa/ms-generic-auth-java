package co.com.pragma.crediya.api.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Respuesta al crear una cuenta a un usuario")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AccountResponse {

    private String username;
    private List<String> roles;
}
