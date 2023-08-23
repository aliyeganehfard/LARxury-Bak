package ir.larxury.message.dispatcher.service;

public interface OTPSender {

    void sendOtp(String code, String receiver);
}
