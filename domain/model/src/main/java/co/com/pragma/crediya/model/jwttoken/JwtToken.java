package co.com.pragma.crediya.model.jwttoken;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class JwtToken {

    private String accessToken;
    private String refreshToken;
    private long expiresInSeconds;
}
