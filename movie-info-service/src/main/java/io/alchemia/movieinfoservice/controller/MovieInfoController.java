package io.alchemia.movieinfoservice.controller;

import io.alchemia.movieinfoservice.model.Movie;
import io.alchemia.movieinfoservice.service.MovieService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/movies")
public class MovieInfoController {

    private final MovieService movieService;

    public MovieInfoController(final MovieService movieService) {
        this.movieService = movieService;
    }

    @RequestMapping("/{id}")
    public Mono<Movie> getMovieDetailedInfo(@PathVariable("id") String id) {
        return movieService.getMovieDetailedInfo(id);
    }

    @RequestMapping("/list")
    public Mono<List<Movie>> getMovieInfoList() {
        return movieService.getMovieInfoList();
    }

}
