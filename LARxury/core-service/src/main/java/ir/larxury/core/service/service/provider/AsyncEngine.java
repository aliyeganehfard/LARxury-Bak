package ir.larxury.core.service.service.provider;

import ir.larxury.common.utils.common.aop.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
public class AsyncEngine {

    @Autowired
    private MessageDispatcherService messageDispatcherService;

    @Async
    public void sendInstantDeliveryMessage(String subject, String message, String receiverEmail) {
        CompletableFuture.runAsync(() -> {
            try {
                messageDispatcherService.instantDelivery(subject, message, receiverEmail);
            } catch (Exception ex) {
                log.error(ErrorCode.CORE_SERVICE_TROUBLE_TO_SEND_INSTANT_DELIVERY.getTechnicalMessage());
                ex.printStackTrace();
            }
        }).whenComplete((t, u) -> {
            if (u != null) {
                log.error(ErrorCode.CORE_SERVICE_TROUBLE_TO_SEND_INSTANT_DELIVERY.getTechnicalMessage());
                u.printStackTrace();
            }
        });
    }
}
