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
        mailService.InstantDelivery("LARxury","welcome to LARxury ALI",
                List.of("aliyeganefard81@gmail.com","aliyegane1381fard@gmail.com",
                        "ali.yegane1753@gmail.com"));
    }

    @Test
    void sendOtp() {
        mailService.send("otp","aliyeganefard81@gmail.com");
    }
}