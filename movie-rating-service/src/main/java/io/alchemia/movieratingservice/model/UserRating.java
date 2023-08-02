package io.alchemia.movieratingservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRating {
    private String id;
    private List<Rating> ratings;

    public void initData(String id) {
        this.setId(id);
        this.setRatings(Arrays.asList(
                 Rating.builder()
                         .id("100")
                         .rating(3)
                         .build(),
                Rating.builder()
                        .id("200")
                        .rating(4)
                        .build()
        ));
    }
}
