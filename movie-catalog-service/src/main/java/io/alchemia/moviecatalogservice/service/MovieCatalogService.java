package io.alchemia.moviecatalogservice.service;

import io.alchemia.moviecatalogservice.model.CatalogItem;
import io.alchemia.moviecatalogservice.model.Movie;
import io.alchemia.moviecatalogservice.model.UserRating;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MovieCatalogService {

    @Value("${info-service.movie-info}")
    private String movieInfoUrl;

    @Value("${rating-service.user-rating}")
    private String userRatingUrl;
    private final WebClient.Builder webClientBuilder;

    public MovieCatalogService(final WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
    }

    public List<CatalogItem> getCatalog(String userId) {

        UserRating userRating =  webClientBuilder.build()
                .get()
                .uri(userRatingUrl.concat(userId))
                .retrieve()
                .bodyToMono(UserRating.class)
                .block();

        return userRating.getRatings()
                .stream()
                .map( rating -> {
                    Movie movie =  webClientBuilder.build()
                            .get()
                            .uri(movieInfoUrl.concat(rating.getId()))
                            .retrieve()
                            .bodyToMono(Movie.class)
                            .block();

                    return CatalogItem.builder()
                            .name(movie.getName())
                            .desc(movie.getDescription())
                            .rating(rating.getRating())
                            .build();
                })
                .collect(Collectors.toList());
    }
}
