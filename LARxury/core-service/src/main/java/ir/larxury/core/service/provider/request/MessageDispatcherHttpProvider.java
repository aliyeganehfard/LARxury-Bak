package ir.larxury.core.service.provider.request;

import ir.larxury.common.utils.common.dto.GeneralResponse;
import ir.larxury.core.service.common.dto.messageDispatcher.DispatcherInstantDeliveryReq;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;

@HttpExchange(url = "http://localhost:8088/v1/email/")
public interface MessageDispatcherHttpProvider {

    @PostExchange("send/instantDelivery")
    ResponseEntity<GeneralResponse> emailInstantDelivery(@RequestBody DispatcherInstantDeliveryReq req,
                                                         @RequestHeader("Authorization") String token);


}
