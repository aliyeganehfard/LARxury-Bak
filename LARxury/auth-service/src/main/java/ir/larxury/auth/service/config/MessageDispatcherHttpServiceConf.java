package ir.larxury.auth.service.config;

import ir.larxury.auth.service.service.provider.request.MessageDispatcherHttpService;
import lombok.SneakyThrows;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

import java.time.Duration;

@Configuration
public class MessageDispatcherHttpServiceConf {

    @Bean
    public MessageDispatcherHttpService getMessageDispatcherHttpService() {
        HttpServiceProxyFactory httpServiceProxyFactory =
                HttpServiceProxyFactory.builder(WebClientAdapter.forClient(webClient()))
                        .blockTimeout(Duration.ofSeconds(20))
                        .build();
        return httpServiceProxyFactory.createClient(MessageDispatcherHttpService.class);
    }

    @Bean
    public WebClient webClient() {
        return WebClient.builder()
                .baseUrl("http://localhost:8088/")
                .build();
    }
}
