package ir.larxury.auth.service.service.provider.request;

import ir.larxury.common.utils.common.dto.GeneralResponse;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;

@HttpExchange(accept = "application/json", contentType = "application/json")
public interface MessageDispatcherHttpService {

    @PostExchange("v1/otp/send/verify")
    GeneralResponse sendVerify(@RequestParam(name = "code") String code,
                               @RequestParam(name = "phoneNumber") String phoneNumber);
}
