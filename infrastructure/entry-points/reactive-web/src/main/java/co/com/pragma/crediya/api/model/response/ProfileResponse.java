package co.com.pragma.crediya.api.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Respuesta al crear perfil a un usuario")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProfileResponse {

    private String firstName;
    private String lastName;
    private String documentId;
    private LocalDate birthDate;
    private String address;
    private String phoneNumber;
    private Double baseSalary;
}
