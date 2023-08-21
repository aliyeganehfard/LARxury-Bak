package ir.larxury.auth.service.service.provider;

import ir.larxury.auth.service.service.provider.request.MessageDispatcherHttpService;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

import java.time.Duration;

@Service
public class MessageDispatcherService {

    public void sendVerify(String code, String phoneNumber) {
        var messageDispatcherRequest = getMessageDispatcherHttpService();
        messageDispatcherRequest.sendVerify(code, phoneNumber);
    }

    @SneakyThrows
    private MessageDispatcherHttpService getMessageDispatcherHttpService() {
        HttpServiceProxyFactory httpServiceProxyFactory =
                HttpServiceProxyFactory.builder(WebClientAdapter.forClient(webClient()))
                        .blockTimeout(Duration.ofSeconds(5))
                        .build();
        return httpServiceProxyFactory.createClient(MessageDispatcherHttpService.class);
    }

    private WebClient webClient() {
        return WebClient.builder()
                .baseUrl("http://localhost:8081/")
                .build();
    }
}
