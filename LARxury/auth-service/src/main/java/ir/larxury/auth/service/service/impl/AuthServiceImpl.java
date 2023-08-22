package ir.larxury.auth.service.service.impl;

import ir.larxury.auth.service.common.aop.exception.AuthException;
import ir.larxury.auth.service.common.dto.authentication.AuthenticationResponse;
import ir.larxury.auth.service.database.model.User;
import ir.larxury.auth.service.config.jwt.JwtService;
import ir.larxury.auth.service.database.model.enums.AuthenticationOperationType;
import ir.larxury.auth.service.service.AuthService;
import ir.larxury.auth.service.service.AuthenticationLogService;
import ir.larxury.auth.service.service.OtpService;
import ir.larxury.auth.service.service.UserService;
import ir.larxury.auth.service.service.provider.MessageDispatcherService;
import ir.larxury.common.utils.common.aop.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import static ir.larxury.common.utils.service.JWTVerificationService.CLAIM_ROLES;
import static ir.larxury.common.utils.service.JWTVerificationService.UUID;

@Slf4j
@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserService userService;

    @Autowired
    private OtpService otpService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationLogService authLogService;

    @Autowired
    private MessageDispatcherService messageDispatcherService;

    @Override
    public AuthenticationResponse signUp(User user, String confirmPassword) {
        if (!user.getPassword().equals(confirmPassword)) {
            log.error(ErrorCode.AUTH_PASSWORD_CONFIRMATION_MISMATCH.getTechnicalMessage());
            throw new AuthException(ErrorCode.AUTH_PASSWORD_CONFIRMATION_MISMATCH);
        }
        var encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        userService.save(user);
        return getAuthenticationResponse(user, AuthenticationOperationType.ACCESS_TOKEN);
    }

    @Override
    public AuthenticationResponse signIn(String username, String password) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password)
            );
        } catch (BadCredentialsException badCredentialsException) {
            var user = userService.findByUsername(username);
            authLogService.failedAttempt(user, AuthenticationOperationType.ACCESS_TOKEN);
            log.error(ErrorCode.AUTH_INCORRECT_PASSWORD.getTechnicalMessage() + " for username {}", username);
            throw new AuthException(ErrorCode.AUTH_INCORRECT_PASSWORD);
        }

        var user = userService.findByUsername(username);
        log.info("user = {} signed in!", user.getUsername());
        return getAuthenticationResponse(user, AuthenticationOperationType.ACCESS_TOKEN);
    }

    @Override
    public AuthenticationResponse signInWithOtp(String phoneNumber, String otpCode) {
        var user = userService.findByPhoneNumber(phoneNumber);
        otpService.validateOTP(user, otpCode);
        return getAuthenticationResponse(user, AuthenticationOperationType.ACCESS_TOKEN);
    }

    @Override
    public String sendOtp(String phoneNumber) {
        var user = userService.findByPhoneNumber(phoneNumber);
        String otp = otpService.sendOtp(user);
        sendOtpAsync(otp, user.getEmail());
        return otp;
    }

    @Async("sendOtpAsync")
    protected void sendOtpAsync(String otp, String email) {
        CompletableFuture.runAsync(() -> {
            try {
                messageDispatcherService.sendVerify(otp, email);
            } catch (Exception ex) {
                log.error(ErrorCode.AUTH_TROUBLE_TO_SEND_OTP.getTechnicalMessage());
                throw new AuthException(ErrorCode.AUTH_TROUBLE_TO_SEND_OTP, ex);
            }
        }).whenComplete((t, u) -> {
            if (u != null) {
                log.error(ErrorCode.AUTH_INTERNAL_ERROR_IN_SENDING_OTP.getTechnicalMessage() + " with this message {}", u.getMessage());
                throw new AuthException(ErrorCode.AUTH_INTERNAL_ERROR_IN_SENDING_OTP);
            }
        });
    }

    @Override
    public AuthenticationResponse refreshToken(String token) {
        var jwt = jwtService.getDecodedJWT(token);
        var user = userService.findByUsername(jwt.getSubject());
        log.info("user {} generate new token from refresh token!", user.getUsername());
        return getAuthenticationResponse(user, AuthenticationOperationType.REFRESH_TOKEN);
    }

    private AuthenticationResponse getAuthenticationResponse(User user, AuthenticationOperationType operationType) {
        List<String> roles = user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
        List<String> uuid = List.of(user.getId().toString());
        Map<String, List<String>> payload = new HashMap<>();
        payload.put(CLAIM_ROLES, roles);
        payload.put(UUID, uuid);
        var token = jwtService.getToken(payload, user);
        authLogService.successAttempt(user, operationType);
        return token;
    }
}
