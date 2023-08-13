package ir.larxury.auth.server.service;

import ir.larxury.auth.server.common.dto.AuthenticationResponse;
import ir.larxury.auth.server.common.dto.SignInDto;
import ir.larxury.auth.server.common.aop.exception.AuthException;
import ir.larxury.auth.server.security.jwt.JwtService;
import ir.larxury.auth.server.database.model.User;
import ir.larxury.auth.server.database.repository.UserRepository;
import ir.larxury.common.utils.common.aop.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static ir.larxury.auth.server.security.jwt.JwtService.CLAIM_ROLES;

@Slf4j
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private OtpService otpService;

    public AuthenticationResponse signUp(User user, String confirmPassword) {
        if (!user.getPassword().equals(confirmPassword)) {
            log.error(ErrorCode.AUTH_PASSWORD_CONFIRMATION_MISMATCH.getTechnicalMessage());
            throw new AuthException(ErrorCode.AUTH_PASSWORD_CONFIRMATION_MISMATCH);
        }
        var encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        userRepository.save(user);
        log.info("save new user with username {}!", user.getUsername());
        return getAuthenticationResponse(user);
    }

    public AuthenticationResponse signIn(SignInDto signInDto) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(signInDto.getUsername(), signInDto.getPassword())
            );
        } catch (BadCredentialsException badCredentialsException) {
            log.error(ErrorCode.AUTH_INCORRECT_PASSWORD.getTechnicalMessage() + " for username {}", signInDto.getUsername());
            throw new AuthException(ErrorCode.AUTH_INCORRECT_PASSWORD);
        }

        var user = findByUsername(signInDto.getUsername());
        log.info("user = {} signed in!", user.getUsername());
        return getAuthenticationResponse(user);
    }

    public String sendOtp(String phoneNumber) {
        var user = findByPhoneNumber(phoneNumber);
        return otpService.save(user);
    }

    public AuthenticationResponse signIn(String phoneNumber, String otpCode) {
        var user = findByPhoneNumber(phoneNumber);
        if (!otpService.validateOTP(user, otpCode)) {
            log.error(ErrorCode.AUTH_OTP_MISMATCH.getTechnicalMessage() + " for user {}", user.getUsername());
            throw new AuthException(ErrorCode.AUTH_OTP_MISMATCH);
        }
        return getAuthenticationResponse(user);
    }

    public AuthenticationResponse refreshToken(String token) {
        var jwt = jwtService.getDecodedJWT(token);
        var user = findByUsername(jwt.getSubject());
        log.info("user {} generate new token from refresh token!", user.getUsername());
        return getAuthenticationResponse(user);
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> {
            log.error(ErrorCode.AUTH_USER_NOT_FOUND.getTechnicalMessage());
            return new AuthException(ErrorCode.AUTH_USER_NOT_FOUND);
        });
    }

    public User findByPhoneNumber(String phoneNumber) {
        return userRepository.findByPhoneNumber(phoneNumber).orElseThrow(() -> {
            log.error(ErrorCode.AUTH_USER_NOT_FOUND_BY_PHONE_NUMBER.getTechnicalMessage() + " phone number is {}", phoneNumber);
            return new AuthException(ErrorCode.AUTH_USER_NOT_FOUND_BY_PHONE_NUMBER);
        });
    }

    private AuthenticationResponse getAuthenticationResponse(User user) {
        List<String> roles = user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
        Map<String, List<String>> payload = new HashMap<>();
        payload.put(CLAIM_ROLES, roles);
        return jwtService.getToken(payload, user);
    }
}
