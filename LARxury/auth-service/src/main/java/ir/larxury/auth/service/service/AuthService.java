package ir.larxury.auth.service.service;

import ir.larxury.auth.service.common.dto.AuthenticationResponse;
import ir.larxury.auth.service.common.dto.SignInDto;
import ir.larxury.auth.service.database.model.User;

public interface AuthService {

     AuthenticationResponse signUp(User user, String confirmPassword);

    AuthenticationResponse signIn(SignInDto signInDto);

    AuthenticationResponse signIn(String phoneNumber, String otpCode);

    String sendOtp(String phoneNumber);

    AuthenticationResponse refreshToken(String token);
}
