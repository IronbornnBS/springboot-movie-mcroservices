package io.alchemia.moviegatewayservice.util;

import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.util.retry.Retry;

import java.time.Duration;

import static io.alchemia.moviecatalogservice.util.Constants.MAX_ATTEMPTS;
import static io.alchemia.moviecatalogservice.util.Constants.TIMEOUT_SECONDS;

@Component
public class WebClientHelper {

    private final WebClient.Builder loadBalancedBuilder;

    public WebClientHelper(WebClient.Builder loadBalancedBuilder) {
        this.loadBalancedBuilder = loadBalancedBuilder;
    }

    public <T> T getWebClientBuilder(HttpMethod httpMethod,
                                      String url,
                                      Class<T> responseDtoClass) {

        return loadBalancedBuilder.build()
                .method(httpMethod)
                .uri(url)
                .retrieve()
                .bodyToMono(responseDtoClass)
                .retryWhen(Retry.backoff(MAX_ATTEMPTS, Duration.ofSeconds(TIMEOUT_SECONDS))
                        .onRetryExhaustedThrow((retryBackoffSpec, retrySignal) -> {
                            throw new RuntimeException(retrySignal.failure());
                        })
                )
                .block();
    }
}
