package co.com.pragma.crediya.api.model.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Modelo de entrada para perfil del usuario")
public class ProfileRequest {

    @Schema(description = "Nombre del usuario", example = "Carlos")
    private String firstName;

    @Schema(description = "Apellido del usuario", example = "Ramírez")
    private String lastName;

    private String email;

    @Schema(description = "Documento de identidad", example = "123456789")
    private String documentId;

    @Schema(description = "Fecha de nacimiento", example = "01/01/1985")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/uuuu")
    private LocalDate birthDate;

    @Schema(description = "Dirección de residencia", example = "Cale 78 # 12-05")
    private String address;

    @Schema(description = "Número de teléfono", example = "+573001234567")
    private String phoneNumber;

    @Schema(description = "Salário base entre 0 y 15.000.000", example = "4500000")
    private Double baseSalary;
}
