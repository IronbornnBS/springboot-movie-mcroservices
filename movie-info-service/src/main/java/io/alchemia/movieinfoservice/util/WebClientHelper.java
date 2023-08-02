package io.alchemia.movieinfoservice.util;

import org.springframework.http.HttpMethod;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.util.retry.Retry;

import java.time.Duration;

import static io.alchemia.movieinfoservice.util.Constants.MAX_ATTEMPTS;
import static io.alchemia.movieinfoservice.util.Constants.TIMEOUT_SECONDS;

public abstract class WebClientHelper {

    public static <T> T getWebClientBuilder(HttpMethod httpMethod,
                                      String url,
                                      Class<T> responseDtoClass) {

        return  WebClient.builder().build()
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
