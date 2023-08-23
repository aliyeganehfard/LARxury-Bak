package ir.larxury.auth.service.config.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import ir.larxury.auth.service.common.dto.authentication.AuthenticationResponse;
import ir.larxury.auth.service.common.aop.exception.AuthException;
import ir.larxury.auth.service.common.utils.PrivateKeyReader;
import ir.larxury.common.utils.common.aop.ErrorCode;
import ir.larxury.common.utils.service.JWTVerificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class JwtService {

    @Autowired
    private JWTVerificationService jwtVerificationService;

    @Autowired
    private Environment env;

    public Long expirationTokenTime;
    public Long expirationRefreshTokenTime;

    public RSAPrivateKey privateKey;
    public RSAPublicKey publicKey;
    public String tokenTypeValue;

    @Autowired
    public void init() {
        try {
            expirationTokenTime = env.getProperty("auth.service.expiration.token.time", Long.class, 30L) * 60L * 1000L;
            expirationRefreshTokenTime = env.getProperty("auth.service.expiration.refresh.token.time", Long.class, 30L) * 60L * 1000L;
            tokenTypeValue = env.getProperty("auth.service.token.type",String.class,"bearer");
            privateKey = PrivateKeyReader.getPrivateKey("sign_key");
            publicKey = jwtVerificationService.getPublicKey();
        } catch (Exception e) {
            log.error(ErrorCode.RSA_TROUBLE_READ_PRIVATE_KEY.getTechnicalMessage());
            throw new AuthException(ErrorCode.RSA_TROUBLE_READ_PRIVATE_KEY, e);
        }
    }

    public AuthenticationResponse getToken(Map<String, List<String>> payload, UserDetails userDetails) {
        AuthenticationResponse jwt = new AuthenticationResponse();
        String accessToken = generateAccessToken(payload, userDetails);
        String refreshToken = generateRefreshToken(userDetails);
        jwt.setAccessToken(accessToken);
        jwt.setRefreshToken(refreshToken);
        jwt.setTokenType(tokenTypeValue);
        log.info("generating token for user {} ", userDetails.getUsername());
        return jwt;
    }

    public DecodedJWT getDecodedJWT(String token) {
        return jwtVerificationService.getDecodedJWT(token);
    }

    private String generateAccessToken(Map<String, List<String>> payload, UserDetails userDetails) {
        JWTCreator.Builder builder = JWT.create()
                .withSubject(userDetails.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + expirationTokenTime));
        payload.forEach(builder::withClaim);
        return builder.sign(getSigningAlgorithm());
    }

    private String generateRefreshToken(UserDetails userDetails) {
        return JWT.create()
                .withSubject(userDetails.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + expirationRefreshTokenTime))
                .sign(getSigningAlgorithm());
    }

    private Algorithm getSigningAlgorithm() {
        return Algorithm.RSA256(publicKey, privateKey);
    }
}