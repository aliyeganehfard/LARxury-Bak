package ir.larxury.auth.service.service;

import ir.larxury.auth.service.database.model.OTP;
import ir.larxury.auth.service.database.model.User;

public interface OtpService {

    OTP save(User user);

    String sendOtp(User user);

    void update(OTP otp);

    void validateOTP(User user, String otpCode);

    OTP findOTPByUserPhoneNumber(User user);
}
