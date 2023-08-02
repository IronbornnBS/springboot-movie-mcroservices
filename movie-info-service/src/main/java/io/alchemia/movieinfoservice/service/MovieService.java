package io.alchemia.movieinfoservice.service;

import io.alchemia.movieinfoservice.model.Movie;
import io.alchemia.movieinfoservice.model.MovieDetailedSummary;
import io.alchemia.movieinfoservice.model.MovieSummary;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

import static io.alchemia.movieinfoservice.util.WebClientHelper.getWebClientBuilder;

@Service
public class MovieService {

    @Value("${movie_db.url.discover.movie}")
    private String movieUrl;

    @Value("${movie_db.url.discover.movies}")
    private String moviesUrl;

    public Mono<Movie> getMovieDetailedInfo(String id) {

        MovieDetailedSummary summary = getWebClientBuilder(HttpMethod.GET,
                String.format(movieUrl, id),
                MovieDetailedSummary.class);

        Movie response = Movie.builder()
                .id(summary.getId())
                .name(summary.getTitle())
                .description(summary.getOverview())
                .build();

        return Mono.just(response);
    }

    public Mono<List<Movie>> getMovieInfoList() {

        MovieSummary movieSummary =  getWebClientBuilder(HttpMethod.GET,
                moviesUrl,
                MovieSummary.class);

        List<Movie> response = movieSummary.getResults()
                .stream()
                .map(results ->
                        Movie.builder()
                                .id(results.getId())
                                .name(results.getOriginal_title())
                                .description(results.getOverview())
                                .build()
                ).toList();

        return Mono.justOrEmpty(response);

    }
}
