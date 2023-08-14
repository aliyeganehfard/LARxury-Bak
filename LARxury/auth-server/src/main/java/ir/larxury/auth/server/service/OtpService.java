package ir.larxury.auth.server.service;

import ir.larxury.auth.server.database.model.OTP;
import ir.larxury.auth.server.database.model.User;

public interface OtpService {

    OTP save(User user);

    String sendOtp(User user);

    void update(OTP otp);

    void validateOTP(User user, String otpCode);

    OTP findOTPByUserPhoneNumber(User user);
}
