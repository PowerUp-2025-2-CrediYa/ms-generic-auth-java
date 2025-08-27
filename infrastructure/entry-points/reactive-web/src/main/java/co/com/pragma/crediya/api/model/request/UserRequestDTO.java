package co.com.pragma.crediya.api.model.request;

import jakarta.validation.constraints.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UserRequestDTO {
    @NotNull(message = "El nombre es obligatorio")
    @Size(min = 1, max = 100)
    private String firstName;

    @NotNull(message = "El apellido es obligatorio")
    @Size(min = 1, max = 100)
    private String lastName;

    @NotNull(message = "El email es obligatorio")
    @Email(message = "El formato del email no es válido")
    @Size(max = 255)
    private String email;

    @NotNull(message = "El documento de identidad es obligatorio")
    @Size(max = 32)
    private String documentId;

    @NotNull(message = "El teléfono es obligatorio")
    @Size(max = 20)
    private String phoneNumber;

    @DecimalMin(value = "0.0", message = "El salario base no puede ser negativo")
    @DecimalMax(value = "15000000.0", message = "El salario base no puede ser mayor a 15,000,000")
    private Double baseSalary;

    private Integer roleId;
}
