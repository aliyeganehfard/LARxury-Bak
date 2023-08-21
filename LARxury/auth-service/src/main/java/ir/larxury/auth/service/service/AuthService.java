package ir.larxury.auth.service.service;

import ir.larxury.auth.service.common.dto.authentication.AuthenticationResponse;
import ir.larxury.auth.service.common.dto.authentication.SignInDto;
import ir.larxury.auth.service.database.model.User;

public interface AuthService {

     AuthenticationResponse signUp(User user, String confirmPassword);

    AuthenticationResponse signIn(String username, String password);

    AuthenticationResponse signInWithOtp(String phoneNumber, String otpCode);

    String sendOtp(String phoneNumber);

    AuthenticationResponse refreshToken(String token);
}
