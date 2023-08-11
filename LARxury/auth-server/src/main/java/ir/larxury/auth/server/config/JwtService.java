package ir.larxury.auth.server.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import ir.larxury.auth.server.common.dto.AuthenticationResponse;
import ir.larxury.auth.server.common.exception.AuthException;
import ir.larxury.auth.server.common.utils.PrivateKeyReader;
import ir.larxury.common.utils.service.JWTVerificationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class JwtService {

    @Autowired
    private JWTVerificationProvider jwtVerificationProvider;

    public static final Long EXPIRATION_TOKEN_TIME = 30L * 60L * 1000L;
    public static final Long EXPIRATION_REFRESH_TOKEN_TIME = 30L * 60L * 1000L;
    public static final String CLAIM_ROLES = "roles";


    public RSAPrivateKey privateKey;
    public RSAPublicKey publicKey;


    @Autowired
    public void init() {
        try {
            privateKey = PrivateKeyReader.getPrivateKey("sign_key");
            publicKey = jwtVerificationProvider.getPublicKey();
        } catch (Exception e) {
           throw new AuthException("problem with read RSA file");
        }
    }

    public AuthenticationResponse getToken(Map<String,List<String>> payload, UserDetails userDetails) {
        AuthenticationResponse jwt = new AuthenticationResponse();
        String accessToken = generateAccessToken(payload, userDetails);
        String refreshToken = generateRefreshToken(userDetails);
        jwt.setAccessToken(accessToken);
        jwt.setRefreshToken(refreshToken);
        return jwt;
    }

    public DecodedJWT getDecodedJWT(String token) {
        return jwtVerificationProvider.getDecodedJWT(token);
    }

    private String generateAccessToken(Map<String,List<String>> payload, UserDetails userDetails) {
        JWTCreator.Builder builder=  JWT.create()
                .withSubject(userDetails.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TOKEN_TIME));
        payload.forEach(builder::withClaim);
        return builder.sign(getSigningAlgorithm());
    }

    private String generateRefreshToken(UserDetails userDetails) {
        return JWT.create()
                .withSubject(userDetails.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_REFRESH_TOKEN_TIME))
                .sign(getSigningAlgorithm());
    }

    private Algorithm getSigningAlgorithm() {
        return Algorithm.RSA256(publicKey,privateKey);
    }

}