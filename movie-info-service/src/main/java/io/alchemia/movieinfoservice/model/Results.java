package io.alchemia.movieinfoservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Results {
    private Integer id;
    private String adult;
    private String original_title;
    private String overview;
    private String release_date;
    private String original_language;
}
