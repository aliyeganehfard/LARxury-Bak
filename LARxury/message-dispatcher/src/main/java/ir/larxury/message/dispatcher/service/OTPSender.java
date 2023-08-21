package ir.larxury.message.dispatcher.service;

public interface OTPSender {

    void send(String code, String receiver);
}
