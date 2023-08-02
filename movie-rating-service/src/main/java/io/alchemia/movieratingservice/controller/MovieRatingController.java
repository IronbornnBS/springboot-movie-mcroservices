package io.alchemia.movieratingservice.controller;

import io.alchemia.movieratingservice.model.Rating;
import io.alchemia.movieratingservice.model.UserRating;
import io.alchemia.movieratingservice.service.RatingService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ratings")
public class MovieRatingController {

    private final RatingService ratingService;

    public MovieRatingController(final RatingService ratingService) {
        this.ratingService = ratingService;
    }

    @RequestMapping("/{id}")
    public Rating getRating(@PathVariable("id") String id) {
        return ratingService.getRating(id);
    }

    @RequestMapping("/user/{id}")
    public UserRating getUserRating(@PathVariable("id") String id) {
        return ratingService.getUserRating(id);
    }
}
