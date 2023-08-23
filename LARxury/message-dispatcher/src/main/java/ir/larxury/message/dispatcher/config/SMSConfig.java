package ir.larxury.message.dispatcher.config;

import ir.larxury.message.dispatcher.service.request.SMSServiceHttpRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

import java.time.Duration;

@Configuration
public class SMSConfig {

    @Bean
    public SMSServiceHttpRequest getMessageDispatcherHttpService() {
        HttpServiceProxyFactory httpServiceProxyFactory =
                HttpServiceProxyFactory.builder(WebClientAdapter.forClient(webClient()))
                        .blockTimeout(Duration.ofSeconds(20))
                        .build();
        return httpServiceProxyFactory.createClient(SMSServiceHttpRequest.class);
    }

    @Bean
    public WebClient webClient() {
        return WebClient.builder()
                .baseUrl("https://api.sms.ir/")
                .build();
    }
}
