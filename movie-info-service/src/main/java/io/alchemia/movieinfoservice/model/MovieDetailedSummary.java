package io.alchemia.movieinfoservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MovieDetailedSummary {
    private Boolean adult;
    private Integer id;
    private BigDecimal budget;
    private String status;
    private String tagline;
    private String title;
    private String release_date;
    private BigDecimal runtime;
    private String overview;
}
