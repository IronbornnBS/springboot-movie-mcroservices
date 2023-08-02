package io.alchemia.movieratingservice.service;

import io.alchemia.movieratingservice.model.Rating;
import io.alchemia.movieratingservice.model.UserRating;
import org.springframework.stereotype.Service;

@Service
public class RatingService {

    public Rating getRating(String id) {
        return Rating.builder()
                .id(id)
                .rating(7)
                .build();
    }

    public UserRating getUserRating(String id) {
        UserRating userRating = new UserRating();
        userRating.initData(id);
        return userRating ;
    }

}
