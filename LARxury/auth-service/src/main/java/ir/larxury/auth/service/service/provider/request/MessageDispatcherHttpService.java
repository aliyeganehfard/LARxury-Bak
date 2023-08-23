package ir.larxury.auth.service.service.provider.request;

import ir.larxury.common.utils.common.dto.GeneralResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;

@HttpExchange(accept = "application/json", contentType = "application/json")
public interface MessageDispatcherHttpService {

    @PostExchange("v1/email/send/verify")
    ResponseEntity<GeneralResponse> sendEmailVerify(@RequestParam(name = "code") String code,
                                                    @RequestParam(name = "email") String email,
                                                    @RequestHeader("Authorization") String token);

    @PostExchange("v1/sms/send/verify")
    ResponseEntity<GeneralResponse> sendSMSVerify(@RequestParam(name = "code") String code,
                                                  @RequestParam(name = "phoneNumber") String phoneNumber,
                                                  @RequestHeader("Authorization") String token);
}
