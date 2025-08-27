package co.com.pragma.crediya.api.model.response;

import lombok.*;

import java.time.ZonedDateTime;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UserResponseDTO {

    private UUID id;
    private String firstName;
    private String lastName;
    private String email;
    private String documentId;
    private String phoneNumber;
    private Double baseSalary;
    private Integer roleId;
    private ZonedDateTime createdAt;
    private ZonedDateTime updatedAt;


}
