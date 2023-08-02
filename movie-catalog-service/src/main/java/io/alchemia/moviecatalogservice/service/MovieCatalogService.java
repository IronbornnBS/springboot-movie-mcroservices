package io.alchemia.moviecatalogservice.service;

import io.alchemia.moviecatalogservice.model.CatalogItem;
import io.alchemia.moviecatalogservice.model.Movie;
import io.alchemia.moviecatalogservice.model.UserRating;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MovieCatalogService {

    public static final int TIMEOUT_SECONDS = 3;
    public static final int MAX_ATTEMPTS = 3;

    @Value("${info-service.movie-info}")
    private String movieInfoUrl;

    @Value("${rating-service.user-rating}")
    private String userRatingUrl;
    private final WebClient.Builder webClientBuilder;

    public MovieCatalogService(final WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
    }

    public List<CatalogItem> getCatalog(String userId) {

        UserRating userRating =  getWebClientBuilder(HttpMethod.GET,
                userRatingUrl.concat(userId),
                UserRating.class);

        return userRating.getRatings()
                .stream()
                .map( rating -> {
                    Movie movie = getWebClientBuilder(HttpMethod.GET,
                            movieInfoUrl.concat(rating.getId()),
                            Movie.class);

                    return CatalogItem.builder()
                            .name(movie.getName())
                            .desc(movie.getDescription())
                            .rating(rating.getRating())
                            .build();
                })
                .collect(Collectors.toList());
    }

    private <T> T getWebClientBuilder(HttpMethod httpMethod,
                                      String url,
                                      Class<T> responseDtoClass) {

        return webClientBuilder.build()
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
