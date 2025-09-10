package co.com.pragma.crediya.model.user;

import lombok.*;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class UserAccount {

    private UUID id;

    private UUID profileId;
    private String username;
    private String hashedPassword;
    private List<String> roles;

}
