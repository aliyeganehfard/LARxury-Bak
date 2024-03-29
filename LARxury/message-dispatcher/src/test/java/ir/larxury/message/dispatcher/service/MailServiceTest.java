package ir.larxury.message.dispatcher.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class MailServiceTest {

    @Autowired
    private MailService mailService;

    @Test
    void instantDelivery() {
        mailService.InstantDelivery("LARxury","welcome to LARxury ALI", "aliyeganefard81@gmail.com");
    }

    @Test
    void sendOtp() {
        mailService.sendOtp("otp","aliyeganefard81@gmail.com");
    }
}