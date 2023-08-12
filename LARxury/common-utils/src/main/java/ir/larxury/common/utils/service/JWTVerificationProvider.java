package ir.larxury.common.utils.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import ir.larxury.common.utils.common.aop.CommonUtilsException;
import ir.larxury.common.utils.common.utils.PublicKeyReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.security.interfaces.RSAPublicKey;

@Service
public class JWTVerificationProvider {

    public static final String CLAIM_ROLES = "roles";
    private RSAPublicKey publicKey;

    private final Logger log = LoggerFactory.getLogger(JWTVerificationProvider.class);

    @Autowired
    public void init() {
        try {
            publicKey = PublicKeyReader.getPublicKey("verify_key.pub");
            log.info("reading public key was successful!");
        } catch (Exception e) {
            log.error("trouble to read public key!");
           throw new CommonUtilsException("problem with read RSA file");
        }
    }

    public RSAPublicKey getPublicKey(){
        return this.publicKey;
    }

    public DecodedJWT getDecodedJWT(String token) {
        try {
            JWTVerifier verifier = JWT.require(getVerificationAlgorithm()).build();
            DecodedJWT decodedJWT = verifier.verify(token);
            log.info("token verified with subject {}", decodedJWT.getSubject());
            return decodedJWT;
        } catch (TokenExpiredException tokenExpiredException) {
            log.error("token was expired!");
            throw new CommonUtilsException("token was expired!");
        } catch (Exception e) {
            log.error("token is invalid!");
            throw new CommonUtilsException("invalid token!");
        }

    }

    private Algorithm getVerificationAlgorithm(){
       return Algorithm.RSA256(publicKey, null);
    }
}