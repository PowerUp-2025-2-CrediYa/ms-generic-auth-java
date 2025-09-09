package co.com.pragma.crediya.model.jwttoken;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class JwtClaims {

    private String subject;
    private List<String> roles;

}
