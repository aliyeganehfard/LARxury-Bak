package ir.larxury.auth.server.service;

import ir.larxury.auth.server.common.exception.handler.AuthException;
import ir.larxury.auth.server.database.model.OTP;
import ir.larxury.auth.server.database.model.User;
import ir.larxury.auth.server.database.repository.OTPRepository;
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
            log.warn("otp code was expired earlier for user with phone number {}", user.getPhoneNumber());
            throw new AuthException(String.format("otp code was expired earlier for user with phone number {%s}", user.getPhoneNumber()));
        }

        if (otp.getIsUsed()) {
            log.warn("otp with code = {} used earlier!", otp.getOtp());
            throw new AuthException(String.format("otp code = {%s} used earlier!", otp.getOtp()));
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
            log.warn("did not find any otp for user with phone number = {}", user.getPhoneNumber());
            throw new AuthException(String.format("did not find any otp for user with phone number = {%s}", user.getPhoneNumber()));
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
