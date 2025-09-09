package co.com.pragma.crediya.jwtprovider;

import co.com.pragma.crediya.model.jwttoken.JwtClaims;
import co.com.pragma.crediya.model.jwttoken.gateways.JwtVerifier;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.RSASSAVerifier;
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
import java.security.PublicKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.text.ParseException;
import java.util.Base64;
import java.util.List;

@Component
public class NimbusJwtVerifier implements JwtVerifier {

    private final PublicKey publicKey;

    public NimbusJwtVerifier(@Value("${jwt.rsa.public}") Resource publicPem) throws Exception {
        this.publicKey = loadPublicKey(publicPem);
    }

    private PublicKey loadPublicKey(Resource pem)
            throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {

        var bytes = Files.readAllBytes(pem.getFile().toPath());
        var content = new String(bytes)
                .replaceAll("-----\\w+ PUBLIC KEY-----", "")
                .replaceAll("\\s", "");

        var decoded = Base64.getDecoder().decode(content); // puede lanzar IllegalArgumentException
        var spec = new X509EncodedKeySpec(decoded);

        return KeyFactory.getInstance("RSA").generatePublic(spec);
    }


    @Override
    public Mono<JwtClaims> verify(String token) {
        return Mono.fromCallable(() -> {
            try {
                SignedJWT jwt = SignedJWT.parse(token);
                JWSVerifier verifier = new RSASSAVerifier((RSAPublicKey) publicKey);
                if (!jwt.verify(verifier)) throw new IllegalArgumentException("Invalid signature");
                JWTClaimsSet c = jwt.getJWTClaimsSet();
                if (c.getExpirationTime() == null || c.getExpirationTime().toInstant().isBefore(java.time.Instant.now())) {
                    throw new IllegalArgumentException("Token expired");
                }
                var roles = (List<String>) c.getClaim("roles");
                return new JwtClaims(c.getSubject(), roles);
            } catch (ParseException | JOSEException e) {
                throw new IllegalArgumentException("Invalid token", e);
            }
        });

    }
}
