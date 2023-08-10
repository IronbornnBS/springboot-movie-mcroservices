package io.alchemia.moviecatalogservice.service;

import io.alchemia.moviecatalogservice.model.CatalogItem;
import io.alchemia.moviecatalogservice.model.Movie;
import io.alchemia.moviecatalogservice.model.UserRating;
import io.alchemia.moviecatalogservice.util.WebClientHelper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MovieCatalogService {

    @Value("${info-service.movie-info}")
    private String movieInfoUrl;

    @Value("${rating-service.user-rating}")
    private String userRatingUrl;

    private final WebClientHelper webClientHelper;

    public MovieCatalogService(WebClientHelper webClientHelper) {
        this.webClientHelper = webClientHelper;
    }

    public List<CatalogItem> getCatalog(String userId) {

        UserRating userRating = webClientHelper.getWebClientBuilder(HttpMethod.GET,
                userRatingUrl.concat(userId),
                UserRating.class);

        return userRating.getRatings()
                .stream()
                .map( rating -> {
                    Movie movie = webClientHelper.getWebClientBuilder(HttpMethod.GET,
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
}