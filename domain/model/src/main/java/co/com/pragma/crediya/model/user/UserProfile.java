package co.com.pragma.crediya.model.user;

import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class UserProfile {

    private UUID id;

    private String firstName;

    private String lastName;

    private String email;

    private String documentId;

    private LocalDate birthDate;

    private String address;

    private String phoneNumber;

    private Double baseSalary;
}
