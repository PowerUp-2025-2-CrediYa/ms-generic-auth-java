package co.com.pragma.crediya.model.user;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class User {
    private UUID id;
    private String firstName;
    private String lastName;
    private String email;
    private String documentId;
    private String phoneNumber;
    private Integer roleId;
    private Double baseSalary;

}
