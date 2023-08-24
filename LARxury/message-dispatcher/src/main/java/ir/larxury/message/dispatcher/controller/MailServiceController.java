package ir.larxury.message.dispatcher.controller;

import ir.larxury.common.utils.common.aop.ErrorCode;
import ir.larxury.common.utils.common.dto.GeneralResponse;
import ir.larxury.message.dispatcher.common.dto.email.req.InstantDeliveryReq;
import ir.larxury.message.dispatcher.service.Notifier;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v1/email/")
public class MailServiceController {

    @Autowired
    @Qualifier("mailService")
    private Notifier notifier;

    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping("send/verify")
    public ResponseEntity<GeneralResponse> sendVerify(@RequestParam(name = "code") String code,
                                                      @RequestParam(name = "email") String email) {
        notifier.sendOtp(code, email);
        var res = GeneralResponse.successfulResponse(ErrorCode.SUCCESSFUL);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

//    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping("send/instantDelivery")
    public ResponseEntity<GeneralResponse> instantDelivery(@RequestBody @Valid InstantDeliveryReq req) {
        notifier.InstantDelivery(req.getSubject(), req.getMessage(), req.getReceiver());
        var res = GeneralResponse.successfulResponse(ErrorCode.SUCCESSFUL);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }
}
