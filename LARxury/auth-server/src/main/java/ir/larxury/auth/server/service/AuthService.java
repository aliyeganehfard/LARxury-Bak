package ir.larxury.auth.server.service;

import ir.larxury.auth.server.common.dto.AuthenticationResponse;
import ir.larxury.auth.server.common.dto.SignInDto;
import ir.larxury.auth.server.database.model.User;

public interface AuthService {

     AuthenticationResponse signUp(User user, String confirmPassword);

    AuthenticationResponse signIn(SignInDto signInDto);

    AuthenticationResponse signIn(String phoneNumber, String otpCode);

    String sendOtp(String phoneNumber);

    AuthenticationResponse refreshToken(String token);
}
