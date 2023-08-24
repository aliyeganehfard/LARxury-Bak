package ir.larxury.message.dispatcher.service;

import java.util.List;

public interface Notifier {

    void InstantDelivery(String subject, String message,String  receiver);

    void sendOtp(String code, String receiver);
}
