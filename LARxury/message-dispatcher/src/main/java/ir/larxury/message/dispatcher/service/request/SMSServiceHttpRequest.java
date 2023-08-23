package ir.larxury.message.dispatcher.service.request;

import ir.larxury.message.dispatcher.common.dto.sms.req.SMSVerifyReq;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;

@HttpExchange(accept = "application/json", contentType = "application/json")
public interface SMSServiceHttpRequest {

    @PostExchange("v1/send/verify")
    void send(@RequestBody SMSVerifyReq req, @RequestHeader("x-api-key") String apiKey);

}
