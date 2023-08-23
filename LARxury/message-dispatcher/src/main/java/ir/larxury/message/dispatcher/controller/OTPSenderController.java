package ir.larxury.message.dispatcher.controller;

import ir.larxury.common.utils.common.aop.ErrorCode;
import ir.larxury.common.utils.common.dto.GeneralResponse;
import ir.larxury.message.dispatcher.service.Notifier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1/email/")
public class OTPSenderController {

    @Autowired
    @Qualifier("mailService")
    private Notifier otpSender;

    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping("send/verify")
    public ResponseEntity<GeneralResponse> sendVerify(@RequestParam(name = "code") String code,
                                                      @RequestParam(name = "phoneNumber") String phoneNumber) {
        otpSender.sendOtp(code, phoneNumber);
        var res = GeneralResponse.successfulResponse(ErrorCode.SUCCESSFUL);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }
}
