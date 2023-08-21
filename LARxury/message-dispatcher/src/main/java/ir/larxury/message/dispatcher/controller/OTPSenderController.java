package ir.larxury.message.dispatcher.controller;

import ir.larxury.message.dispatcher.service.OTPSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1/otp/")
public class OTPSenderController {

    @Autowired
    @Qualifier("mailService")
    private OTPSender otpSender;

    @PostMapping("send/verify")
    public void sendVerify(@RequestParam(name = "code") String code,
                           @RequestParam(name = "phoneNumber") String phoneNumber) {
        otpSender.send(code, phoneNumber);
    }
}
