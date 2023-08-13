package ir.larxury.auth.server.service;

import ir.larxury.auth.server.common.aop.exception.AuthException;
import ir.larxury.auth.server.database.model.OTP;
import ir.larxury.auth.server.database.model.User;
import ir.larxury.auth.server.database.repository.OTPRepository;
import ir.larxury.common.utils.common.aop.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Random;

@Slf4j
@Service
public class OtpService {

    @Autowired
    private OTPRepository otpRepository;

    @Autowired
    private Environment env;

    public Integer expirationTime;
    public Integer otpLength;

    @Autowired
    public void init() {
        expirationTime = env.getProperty("auth.server.otp.expiration.time", Integer.class, 2);
        otpLength = env.getProperty("auth.server.otp.length", Integer.class, 6);
    }


    public String save(User user) {
        var otp = new OTP();
        otp.setUser(user);
        otp.setOtp(generateOTPCode());
        otp.setExpirationDate(new Date(System.currentTimeMillis() + expirationTime));
        otp.setIsUsed(false);
        otpRepository.save(otp);
        log.info("save otp for username = {} and otp = {} !", user.getUsername(), otp.getOtp());
        return otp.getOtp();
    }

    public void update(OTP otp) {
        log.info("update otp with id {}", otp.getId());
        otpRepository.save(otp);
    }

    public Boolean validateOTP(User user, String otpCode) {
        var otp = findOTPByUserPhoneNumber(user);

        if (new Date().after(otp.getExpirationDate())) {
            log.warn(ErrorCode.AUTH_OTP_EXPIRED.getTechnicalMessage(), " for user {}", user.getUsername());
            throw new AuthException(ErrorCode.AUTH_OTP_EXPIRED);
        }

        if (otp.getIsUsed()) {
            log.warn(ErrorCode.AUTH_OTP_IS_ALREADY_USED.getTechnicalMessage() + " otp is {}", otp);
            throw new AuthException(ErrorCode.AUTH_OTP_IS_ALREADY_USED);
        }

        if (!otp.getOtp().equals(otpCode)) {
            log.warn("otp mismatch for username = {} and otp = {}", user.getUsername(), otpCode);
            return false;
        }

        otp.setIsUsed(true);
        this.update(otp);
        return true;
    }

    public OTP findOTPByUserPhoneNumber(User user) {
        OTP otp = otpRepository.findFirstByUserPhoneNumberOrderByCreateDateDesc(user.getPhoneNumber());
        if (otp == null) {
            log.warn(ErrorCode.AUTH_OTP_NOT_FOUND_BY_PHONE_NUMBER.getTechnicalMessage() + " phone number is {}", user.getPhoneNumber());
            throw new AuthException(ErrorCode.AUTH_OTP_NOT_FOUND_BY_PHONE_NUMBER);
        }
        return otp;
    }

    private String generateOTPCode() {
        Random random = new Random();
        StringBuilder otp = new StringBuilder(otpLength);

        for (int i = 0; i < otpLength; i++) {
            otp.append(random.nextInt(10));
        }

        return otp.toString();
    }

}
