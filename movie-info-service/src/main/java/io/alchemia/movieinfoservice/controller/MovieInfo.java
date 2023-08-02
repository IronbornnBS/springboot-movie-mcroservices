package io.alchemia.movieinfoservice.controller;

import io.alchemia.movieinfoservice.model.Movie;
import io.alchemia.movieinfoservice.service.MovieService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/movies")
public class MovieInfo {

    private final MovieService movieService;

    public MovieInfo(final MovieService movieService) {
        this.movieService = movieService;
    }

//    @RequestMapping("/{id}")
//    public Movie getMovieInfo(@PathVariable("id") String id) {
//        return movieService.getMovieInfo(id);
//    }

    @RequestMapping("/{page}")
    public List<Movie> getMovieInfoByName(@PathVariable("page") String page) {
        return movieService.getMovieInfoByName(page);
    }

}
