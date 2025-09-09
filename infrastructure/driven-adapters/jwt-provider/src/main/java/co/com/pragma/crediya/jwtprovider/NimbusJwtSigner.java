package co.com.pragma.crediya.jwtprovider;

import co.com.pragma.crediya.model.jwttoken.JwtToken;
import co.com.pragma.crediya.model.jwttoken.gateways.JwtSigner;
import co.com.pragma.crediya.model.user.User;
import com.nimbusds.jose.JOSEObjectType;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.nio.file.Files;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;


@Component
public class NimbusJwtSigner implements JwtSigner {

    private final String issuer;
    private final String audience;
    private final long accessSeconds;
    private final long refreshSeconds;
    private final PrivateKey privateKey;

    public NimbusJwtSigner(
            @Value("${jwt.issuer}") String issuer,
            @Value("${jwt.audience}") String audience,
            @Value("${jwt.access-token-seconds}") long accessSeconds,
            @Value("${jwt.refresh-token-seconds}") long refreshSeconds,
            @Value("${jwt.rsa.private}") Resource privatePem
    ) throws Exception {
        this.issuer = issuer;
        this.audience = audience;
        this.accessSeconds = accessSeconds;
        this.refreshSeconds = refreshSeconds;
        this.privateKey = loadPrivateKey(privatePem);
    }

    private PrivateKey loadPrivateKey(Resource pem)
            throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {

        var bytes = Files.readAllBytes(pem.getFile().toPath());
        var content = new String(bytes)
                .replaceAll("-----\\w+ PRIVATE KEY-----", "")
                .replaceAll("\\s", "");

        var decoded = Base64.getDecoder().decode(content); // puede lanzar IllegalArgumentException
        var spec = new PKCS8EncodedKeySpec(decoded);

        return KeyFactory.getInstance("RSA").generatePrivate(spec);
    }


    @Override
    public Mono<JwtToken> sign(User user) {
        return Mono.fromCallable(() -> {
            var now = Instant.now();
            var exp = now.plusSeconds(accessSeconds);
            var claims = new JWTClaimsSet.Builder()
                    .subject(user.getEmail())
                    .issuer(issuer)
                    .audience(audience)
                    .claim("roles", user.getRoles())
                    .issueTime(Date.from(now))
                    .expirationTime(Date.from(exp))
                    .build();

            var header = new JWSHeader.Builder(JWSAlgorithm.RS256).type(JOSEObjectType.JWT).build();
            var signed = new SignedJWT(header, claims);
            signed.sign(new RSASSASigner(privateKey));
            String access = signed.serialize();

            var refreshExp = now.plusSeconds(refreshSeconds);
            var refreshClaims = new JWTClaimsSet.Builder(claims).expirationTime(Date.from(refreshExp)).build();
            var refresh = new SignedJWT(header, refreshClaims);
            refresh.sign(new RSASSASigner(privateKey));
            String refreshToken = refresh.serialize();

            return new JwtToken(access, refreshToken, accessSeconds);
        });
    }
}
