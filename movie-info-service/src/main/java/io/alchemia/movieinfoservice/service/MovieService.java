package io.alchemia.movieinfoservice.service;

import io.alchemia.movieinfoservice.model.Movie;
import io.alchemia.movieinfoservice.model.MovieSummary;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MovieService {

    @Value("${movie_db.api_key}")
    private String apiKey;

    @Value("${movie_db.url.discover.movies}")
    private String moviesUrl;

    private final WebClient.Builder webClientBuilder;

    public MovieService(final WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
    }

    public Movie getMovieInfo(String id) {

        MovieSummary movieSummary =  webClientBuilder.build()
                .get()
                .uri("https://api.themoviedb.org/3/movie/" + id)
                .header("accept", "application/json")
                .header("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJhYjZmZGVmYzYwNzlhMmI1N2ZmODEwZTliYzk1NTRhYiIsInN1YiI6IjYwZTRjNmNlZGIxNTRmMDA1ZTRkNjhhOCIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.9sUYvF3MC6KYwZ_54RyvO6BPqjLb4eZEH7mIESEN7BU")
                .retrieve()
                .bodyToMono(MovieSummary.class)
                .block();

        return Movie.builder()
                .id(movieSummary.getResults().get(0).getId())
                .name(movieSummary.getResults().get(0).getOriginal_title())
                .description(movieSummary.getPage().toString())
                .build();
    }

    public List<Movie> getMovieInfoByName(String page) {

        MovieSummary movieSummary =  webClientBuilder.build()
                .get()
                .uri( String.format(moviesUrl, page, apiKey))
                .retrieve()
                .bodyToMono(MovieSummary.class)
                .block();

        return movieSummary != null ? movieSummary.getResults()
                .stream().map(results ->
                        Movie.builder()
                                .id(results.getId())
                                .name(results.getOriginal_title())
                                .description(results.getOverview())
                                .build()
                ).collect(Collectors.toList()) : null;

    }
}
