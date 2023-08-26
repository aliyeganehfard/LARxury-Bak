package ir.larxury.core.service.config.provider;

import ir.larxury.core.service.service.provider.request.AuthHttpService;
import ir.larxury.core.service.service.provider.request.MessageDispatcherHttpService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

import java.time.Duration;

@Configuration
public class HttpRequestConfig {

    public static final Integer DEFAULT_TIMOUT = 20;

    @Bean
    AuthHttpService authProviderConfig(WebClient webClient) {
        HttpServiceProxyFactory httpServiceProxyFactory =
                HttpServiceProxyFactory.builder(WebClientAdapter.forClient(webClient))
                        .blockTimeout(Duration.ofSeconds(DEFAULT_TIMOUT))
                        .build();
        return httpServiceProxyFactory.createClient(AuthHttpService.class);
    }

    @Bean
    MessageDispatcherHttpService getMessageDispatcherHttpServiceConfig(WebClient webClient) {
        HttpServiceProxyFactory httpServiceProxyFactory =
                HttpServiceProxyFactory.builder(WebClientAdapter.forClient(webClient))
                        .blockTimeout(Duration.ofSeconds(DEFAULT_TIMOUT))
                        .build();
        return httpServiceProxyFactory.createClient(MessageDispatcherHttpService.class);
    }

    @Bean
    WebClient webClient() {
        return WebClient.builder()
                .build();
    }
}
