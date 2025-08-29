package co.com.pragma.crediya.api.model.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Respuesta al crear un usuario")
public class UserResponse {

    @Schema(description = "ID único del usuario", example = "550e8400-e29b-41d4-a716-446655440000")
    private UUID id;

    @Schema(description = "Nombre del usuario", example = "Carlos")
    private String firstName;

    @Schema(description = "Apellido del usuario", example = "Ramírez")
    private String lastName;

    @Schema(description = "Correo electrónico registrado", example = "carlos@example.com")
    private String email;

    @Schema(description = "Documento de identidad", example = "123456789")
    private String documentId;

    @Schema(description = "Número de teléfono", example = "+573001234567")
    private String phoneNumber;

    @Schema(description = "ID del rol", example = "2")
    private Integer roleId;

    @Schema(description = "Salario base entre 0 y 15.000.000", example = "4500000")
    private Double baseSalary;
}

