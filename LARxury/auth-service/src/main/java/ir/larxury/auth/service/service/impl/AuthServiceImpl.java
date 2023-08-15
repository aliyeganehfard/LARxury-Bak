package ir.larxury.auth.service.service.impl;

import ir.larxury.auth.service.common.aop.exception.AuthException;
import ir.larxury.auth.service.common.dto.AuthenticationResponse;
import ir.larxury.auth.service.common.dto.SignInDto;
import ir.larxury.auth.service.database.model.User;
import ir.larxury.auth.service.security.jwt.JwtService;
import ir.larxury.auth.service.service.AuthService;
import ir.larxury.auth.service.service.OtpService;
import ir.larxury.auth.service.service.UserService;
import ir.larxury.common.utils.common.aop.ErrorCode;
import ir.larxury.common.utils.service.UserSecurityService;
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

import static ir.larxury.auth.service.security.jwt.JwtService.CLAIM_ROLES;

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

    @Override
    public AuthenticationResponse signUp(User user, String confirmPassword) {
        if (!user.getPassword().equals(confirmPassword)) {
            log.error(ErrorCode.AUTH_PASSWORD_CONFIRMATION_MISMATCH.getTechnicalMessage());
            throw new AuthException(ErrorCode.AUTH_PASSWORD_CONFIRMATION_MISMATCH);
        }
        var encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        userService.save(user);
        return getAuthenticationResponse(user);
    }

    @Override
    public AuthenticationResponse signIn(SignInDto signInDto) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(signInDto.getUsername(), signInDto.getPassword())
            );
        } catch (BadCredentialsException badCredentialsException) {
            log.error(ErrorCode.AUTH_INCORRECT_PASSWORD.getTechnicalMessage() + " for username {}", signInDto.getUsername());
            throw new AuthException(ErrorCode.AUTH_INCORRECT_PASSWORD);
        }



        var user = userService.findByUsername(signInDto.getUsername());
        log.info("user = {} signed in!", user.getUsername());
        return getAuthenticationResponse(user);
    }

    @Override
    public AuthenticationResponse signIn(String phoneNumber, String otpCode) {
        var user = userService.findByPhoneNumber(phoneNumber);
        otpService.validateOTP(user, otpCode);
        return getAuthenticationResponse(user);
    }

    @Override
    public String sendOtp(String phoneNumber) {
        var user = userService.findByPhoneNumber(phoneNumber);
        return otpService.sendOtp(user);
    }

    @Override
    public AuthenticationResponse refreshToken(String token) {
        var jwt = jwtService.getDecodedJWT(token);
        var user = userService.findByUsername(jwt.getSubject());
        log.info("user {} generate new token from refresh token!", user.getUsername());
        return getAuthenticationResponse(user);
    }

    private AuthenticationResponse getAuthenticationResponse(User user) {
        List<String> roles = user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
        Map<String, List<String>> payload = new HashMap<>();
        payload.put(CLAIM_ROLES, roles);
        return jwtService.getToken(payload, user);
    }
}
