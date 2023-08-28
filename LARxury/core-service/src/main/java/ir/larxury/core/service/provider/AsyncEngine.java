package ir.larxury.core.service.provider;

import ir.larxury.common.utils.common.aop.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
@PropertySource(value = "classpath:core-service.properties")
public class AsyncEngine {

    @Autowired
    private MessageDispatcherProvider messageDispatcherProvider;

    @Autowired
    private AuthServiceProvider authServiceProvider;

    @Value("${core.service.notifier.enabled}")
    private Boolean isNotifierEnable;

    @Async
    public void sendEmailInstantDeliveryMessage(String userId, String subject, String message) {
        if (isNotifierEnable) {
            CompletableFuture.runAsync(() -> {
                try {
                    var userInfo = authServiceProvider.getUserInfo(userId);
                    messageDispatcherProvider.instantDelivery(subject, message, userInfo.getEmail());
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
}
